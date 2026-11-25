package edu.iset.soa.tp1;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public final class GenererSchema {
    public static void main(String[] args) throws Exception {
        JAXBContext.newInstance(Message.class).generateSchema(new SchemaOutputResolver() {
            @Override public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                File file = new File("message.xsd");
                StreamResult output = new StreamResult(file);
                output.setSystemId(file.toURI().toString());
                return output;
            }
        });
        System.out.println("Schéma généré : message.xsd");
    }
}
