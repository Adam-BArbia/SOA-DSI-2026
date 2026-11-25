#!/usr/bin/env python3
"""Check source and example syntax; not a substitute for Maven tests."""
from pathlib import Path
import json, sys, xml.etree.ElementTree as ET
root=Path(__file__).resolve().parents[1]
errs=[]
for f in root.rglob('*'):
 if not f.is_file() or 'target' in f.parts: continue
 if f.suffix in {'.xml','.xsd','.wsdl'} or f.name=='pom.xml':
  try:ET.parse(f)
  except Exception as ex:errs.append(f'{f.relative_to(root)}: invalid XML: {ex}')
 if f.suffix=='.json':
  try:json.loads(f.read_text(encoding='utf-8'))
  except Exception as ex:errs.append(f'{f.relative_to(root)}: invalid JSON: {ex}')
 if f.suffix=='.pdf':
  with open(f,'rb') as b:
   if b.read(5)!=b'%PDF-':errs.append(f'{f.relative_to(root)}: invalid PDF header')
try:
 from lxml import etree
 xmldir=root/'TD/TD01-XML/exemples'
 dtd=etree.DTD(str(xmldir/'universite.dtd'))
 for n in ['universite-corrige.xml','exercice4-corrige.xml']:
  parsed=etree.parse(str(xmldir/n))
  if not dtd.validate(parsed):errs.append(n+': fails DTD '+str(dtd.error_log))
 schema=etree.XMLSchema(etree.parse(str(xmldir/'etudiant.xsd')))
 if not schema.validate(etree.parse(str(xmldir/'etudiant-valide.xml'))):errs.append('etudiant-valide.xml fails XSD')
except ImportError:print('Optional lxml missing; skipping DTD/XSD constraint checks')
for e in errs:print('ERROR',e)
if errs:print('FAILED:',len(errs));sys.exit(1)
print(f'PASS: {sum(1 for f in root.rglob("*") if f.is_file())} files checked; XML, JSON and PDF signatures valid, DTD/XSD checked when available')
