#!/usr/bin/env python3
"""Runtime evidence probes for SOA TPs (Python standard library only).

Run inside GitHub Actions against services started by the workflow. JSON files
are actual observed requests/responses; no example is presented as evidence.
"""
import json
import os
import sys
import time
import urllib.error
import urllib.parse
import urllib.request
import xml.etree.ElementTree as ET
from http.server import BaseHTTPRequestHandler, HTTPServer
from pathlib import Path

OUT = Path(os.environ.get("EVIDENCE_DIR", "evidence"))
EVENTS = []


def log(case, status, detail, response=None):
    entry = {"case": case, "status": status, "detail": detail}
    if response is not None:
        entry["response_excerpt"] = str(response)[:1800]
    EVENTS.append(entry)
    print(f"[{status.upper()}] {case}: {detail}", flush=True)


def save():
    OUT.mkdir(parents=True, exist_ok=True)
    (OUT / "observations.json").write_text(
        json.dumps(EVENTS, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    rows = ["# Observations du workflow", "", "Exécution distante automatisée : GitHub Actions. ",
            "Ce fichier ne constitue pas une capture d'interface locale.", "",
            "| Vérification | Statut | Détail |", "| --- | --- | --- |"]
    for e in EVENTS:
        detail = e["detail"].replace("|", "/").replace("\n", " ")[:260]
        rows.append(f"| {e['case']} | {e['status']} | {detail} |")
    rows.append("")
    (OUT / "observations.md").write_text("\n".join(rows), encoding="utf-8")


def request(url, method="GET", body=None, headers=None, timeout=25):
    data = None
    if body is not None:
        data = body.encode("utf-8") if isinstance(body, str) else body
    req = urllib.request.Request(url, data=data, headers=headers or {}, method=method)
    try:
        with urllib.request.urlopen(req, timeout=timeout) as response:
            return response.status, response.read().decode("utf-8", errors="replace")
    except urllib.error.HTTPError as exc:
        return exc.code, exc.read().decode("utf-8", errors="replace")


def json_request(url, method="GET", payload=None):
    body = None if payload is None else json.dumps(payload)
    status, raw = request(url, method, body, {"Content-Type": "application/json"})
    try:
        parsed = json.loads(raw)
    except ValueError:
        parsed = raw
    return status, parsed


def wait_ready(url, seconds=100):
    start = time.monotonic()
    while time.monotonic() - start < seconds:
        try:
            status, _ = request(url, timeout=4)
            if status < 500:
                log("readiness", "pass", f"{url} -> HTTP {status}")
                return
        except (OSError, TimeoutError):
            pass
        time.sleep(2)
    raise RuntimeError(f"Service did not become ready: {url}")


def assert_status(actual, allowed, label):
    if actual not in allowed:
        raise AssertionError(f"{label}: HTTP {actual}, expected {allowed}")


def run_rest(variant):
    if variant == "data":
        base = "http://127.0.0.1:8080/data"
        wait_ready(base)
        for name, url in [
            ("root HAL", base),
            ("activity list", base + "/activities"),
            ("category list", base + "/categories"),
            ("status search", base + "/activities/search/findByStatus?status=OPEN"),
            ("category search", base + "/activities/search/findByCategoryId?categoryId=1"),
        ]:
            status, result = json_request(url)
            assert_status(status, {200}, name)
            if not isinstance(result, dict):
                raise AssertionError(f"{name}: expected HAL object")
            if name == "root HAL" and "_links" not in result:
                raise AssertionError("Spring Data REST should supply _links")
            log(name, "pass", f"HTTP {status}, keys={list(result.keys())}", json.dumps(result)[:700])
        return

    base = "http://127.0.0.1:8080/api"
    wait_ready(base + "/activities")
    for name, path in [
        ("activity list", "/activities"),
        ("category list", "/categories"),
        ("by status", "/activities/status/OPEN"),
        ("by category", "/activities/category/1"),
        ("by name", "/activities/search/Foot"),
    ]:
        status, result = json_request(base + path)
        assert_status(status, {200}, name)
        if not isinstance(result, list):
            raise AssertionError(f"{name}: expected list, got {type(result).__name__}")
        log(name, "pass", f"HTTP {status}, {len(result)} entries", json.dumps(result)[:700])

    item = {"name": "Atelier verification CI", "description": "Test HTTP reel",
            "localDate": "2026-12-15", "location": "Sousse",
            "capacity": 24, "price": 40.0, "status": "OPEN", "category": {"id": 1}}
    status, created = json_request(base + "/activities", "POST", item)
    assert_status(status, {201}, "POST activity")
    identifier = created.get("id")
    if not identifier:
        raise AssertionError("Creation response has no id")
    log("create activity", "pass", f"HTTP {status}, id={identifier}", json.dumps(created)[:700])

    status, retrieved = json_request(base + f"/activities/{identifier}")
    assert_status(status, {200}, "GET created")
    if retrieved.get("name") != item["name"]:
        raise AssertionError("Created activity not found")
    log("read activity", "pass", f"HTTP {status}, persisted name correct")

    item["name"] = "Atelier CI modifie"
    status, updated = json_request(base + f"/activities/{identifier}", "PUT", item)
    assert_status(status, {200}, "PUT activity")
    if updated.get("name") != item["name"]:
        raise AssertionError("Update did not persist")
    log("update activity", "pass", f"HTTP {status}, modified name correct")

    status, _ = request(base + f"/activities/{identifier}", method="DELETE")
    assert_status(status, {204}, "DELETE activity")
    status, _ = request(base + f"/activities/{identifier}")
    assert_status(status, {404}, "GET deleted activity")
    log("delete activity", "pass", "HTTP 204 followed by HTTP 404")


def soap_request(endpoint, operation, argument=None):
    namespace = "http://service.tp3.soa.iset.edu/"
    inner = "" if argument is None else argument
    payload = (
        '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" '
        f'xmlns:tns="{namespace}"><soapenv:Body><tns:{operation}>'
        f'{inner}</tns:{operation}></soapenv:Body></soapenv:Envelope>'
    )
    status, body = request(endpoint, "POST", payload,
                           {"Content-Type": "text/xml; charset=utf-8", "SOAPAction": '""'})
    return status, body


def run_soap3():
    endpoint = "http://127.0.0.1:8080/BanqueWS"
    wait_ready(endpoint + "?wsdl")
    status, wsdl = request(endpoint + "?wsdl")
    ET.fromstring(wsdl)
    if "BanqueService" not in wsdl or "conversionEuroToDinar" not in wsdl:
        raise AssertionError("Generated WSDL is missing expected names")
    log("generated WSDL", "pass", f"HTTP {status}; actual JAX-WS WSDL", wsdl[:700])
    (OUT / "wsdl-generated.xml").write_text(wsdl, encoding="utf-8")
    for operation, argument in [
        ("conversionEuroToDinar", "<montant>100</montant>"),
        ("getCompte", "<code>1</code>"),
        ("getComptes", None),
    ]:
        status, raw = soap_request(endpoint, operation, argument)
        assert_status(status, {200}, operation)
        root = ET.fromstring(raw)
        if any(e.tag.endswith("Fault") for e in root.iter()):
            raise AssertionError(f"{operation}: SOAP Fault: {raw[:700]}")
        if operation == "conversionEuroToDinar":
            values = [e.text for e in root.iter() if e.tag.split("}")[-1] == "return"]
            if not values or abs(float(values[0]) - 350) > 0.01:
                raise AssertionError(f"Unexpected conversion: {raw[:700]}")
        elif operation == "getCompte" and "solde" not in raw:
            raise AssertionError("No account balance in SOAP response")
        elif operation == "getComptes" and raw.count("<return>") < 3:
            # Namespace-prefixed SOAP responses can contain return elements with prefixes.
            if sum(e.tag.split("}")[-1] == "return" for e in root.iter()) < 3:
                raise AssertionError("Expected three account elements")
        log(operation, "pass", f"SOAP HTTP {status}, XML parsed", raw[:1200])
        (OUT / f"soap-{operation}.xml").write_text(raw, encoding="utf-8")


def run_graphql():
    base = "http://127.0.0.1:8090/graphql"
    wait_ready("http://127.0.0.1:8090/graphiql")
    def query(name, document, vars=None):
        status, output = json_request(base, "POST", {"query": document, "variables": vars or {}})
        assert_status(status, {200}, name)
        if not isinstance(output, dict) or output.get("errors"):
            raise AssertionError(f"{name} GraphQL errors: {output}")
        log(name, "pass", f"GraphQL HTTP {status}", json.dumps(output, ensure_ascii=False)[:900])
        return output["data"]

    first = query("1 products", "query{productList{id name price quantity}}")
    if len(first["productList"]) < 30:
        raise AssertionError("Expected >= 30 seed products")
    categories = query("2 categories", "query{categories{id name}}")["categories"]
    if len(categories) < 3:
        raise AssertionError("Expected >= 3 seed categories")
    category_id = categories[0]["id"]
    product_id = first["productList"][0]["id"]
    query("3 products with category", "query{productList{id name category{id name}}}")
    query("4 categories with products", "query{categories{id name products{id name}}}")
    query("5 product by id", "query($id:ID!){productById(id:$id){id name}}", {"id": product_id})
    query("6 category by id", "query($id:ID!){categoryById(id:$id){id name}}", {"id": category_id})
    saved = query("7 save", 'mutation($categoryId:ID!){saveProduct(product:{name:"Demo CI",price:1299.0,quantity:8,categoryId:$categoryId}){id name price}}', {"categoryId": category_id})
    created_id = saved["saveProduct"]["id"]
    read = query("8 verify save", "query($id:ID!){productById(id:$id){id name}}", {"id": created_id})
    if read["productById"]["name"] != "Demo CI":
        raise AssertionError("Created product mismatch")
    updated = query("9 update", 'mutation($id:ID!,$categoryId:ID!){updateProduct(id:$id,product:{name:"Demo CI Modifie",price:1199.0,quantity:12,categoryId:$categoryId}){id name price quantity}}',
                    {"id": created_id, "categoryId": category_id})
    if updated["updateProduct"]["quantity"] != 12:
        raise AssertionError("Update quantity mismatch")
    read = query("10 verify update", "query($id:ID!){productById(id:$id){name quantity}}", {"id": created_id})
    if read["productById"]["quantity"] != 12:
        raise AssertionError("Updated product not persisted")
    deleted = query("11 delete", "mutation($id:ID!){deleteProduct(id:$id)}", {"id": created_id})
    if deleted["deleteProduct"] != "DELETED":
        raise AssertionError("Delete result mismatch")
    read = query("12 verify delete", "query($id:ID!){productById(id:$id){id}}", {"id": created_id})
    if read["productById"] is not None:
        raise AssertionError("Product remains after delete")


class MockWeather(BaseHTTPRequestHandler):
    def do_GET(self):
        parsed = urllib.parse.urlparse(self.path)
        params = urllib.parse.parse_qs(parsed.query)
        city = params.get("q", [""])[0]
        if not params.get("appid") or not city or params.get("units") != ["metric"]:
            self.send_error(400)
            return
        payload = json.dumps({
            "name": city, "main": {"temp": 22.5, "humidity": 58},
            "weather": [{"description": "ciel clair", "main": "Clear", "id": 800, "icon": "01d"}],
            "wind": {"speed": 3.2}
        }).encode("utf-8")
        self.send_response(200)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(payload)))
        self.end_headers()
        self.wfile.write(payload)
    def log_message(self, fmt, *args):
        # Suppress dummy credential in upstream URL access logs.
        pass


def weather_upstream():
    print("Isolated upstream simulator on port 18080", flush=True)
    HTTPServer(("127.0.0.1", 18080), MockWeather).serve_forever()


def run_weather():
    base = "http://127.0.0.1:8080/api/meteo/"
    wait_ready("http://127.0.0.1:8080/api/meteo/Tunis")
    for city in ("Tunis", "Sousse"):
        status, obj = json_request(base + city)
        assert_status(status, {200}, city)
        if obj.get("name") != city or not obj.get("main", {}).get("temp"):
            raise AssertionError(f"Weather DTO mismatch: {obj}")
        log("weather " + city, "pass", "HTTP 200; real app -> simulated upstream (not live OpenWeatherMap)",
            json.dumps(obj, ensure_ascii=False))


def run_soap2():
    directory = Path("TP/TP02-SOAPUI/requetes")
    targets = [
        ("Calculator Add", "https://www.dneonline.com/calculator.asmx",
         directory / "calculator-add.xml", "http://tempuri.org/Add"),
        ("CountryInfoService TN", "https://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso",
         directory / "country-full-info.xml", "http://www.oorsprong.org/websamples.countryinfo/FullCountryInfo"),
    ]
    for name, url, path, action in targets:
        try:
            status, body = request(url, "POST", path.read_bytes(), {
                "Content-Type": "text/xml; charset=utf-8", "SOAPAction": f'"{action}"',
                "User-Agent": "SOA-DSI-2026-learning-verification/1.0",
            }, timeout=16)
            if status == 200 and ("Envelope" in body or "envelope" in body):
                root = ET.fromstring(body)
                if any(e.tag.endswith("Fault") for e in root.iter()):
                    log(name, "inconclusive", "SOAP Fault from external provider", body[:800])
                    continue
                if name == "Calculator Add":
                    vals = [e.text for e in root.iter() if e.tag.split("}")[-1] == "AddResult"]
                    if not vals or vals[0] != "50":
                        log(name, "fail", "Unexpected AddResult", body[:800])
                        continue
                log(name, "pass", "HTTP 200; response from public service (not SoapUI GUI)",
                    body[:800])
                (OUT / ("calculator-response.xml" if "Calculator" in name else "country-response.xml")).write_text(body, encoding="utf-8")
            else:
                log(name, "inconclusive", f"External endpoint HTTP {status}", body[:500])
        except (OSError, ET.ParseError, TimeoutError) as err:
            log(name, "inconclusive", f"External endpoint inaccessible: {type(err).__name__}: {err}")


def main():
    mode = sys.argv[1] if len(sys.argv) > 1 else ""
    if mode == "weather-upstream":
        weather_upstream()
        return
    OUT.mkdir(parents=True, exist_ok=True)
    try:
        if mode == "rest":
            run_rest(sys.argv[2])
        elif mode == "soap3":
            run_soap3()
        elif mode == "graphql":
            run_graphql()
        elif mode == "weather":
            run_weather()
        elif mode == "soap2":
            run_soap2()
        else:
            raise ValueError(f"Unknown verification mode: {mode}")
    except Exception as err:
        log("execution", "fail", f"{type(err).__name__}: {err}")
        raise
    finally:
        save()


if __name__ == "__main__":
    main()
