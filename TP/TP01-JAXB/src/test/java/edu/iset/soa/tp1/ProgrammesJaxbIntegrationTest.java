package edu.iset.soa.tp1;

import jakarta.xml.bind.JAXBContext;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import static org.junit.jupiter.api.Assertions.*;

class ProgrammesJaxbIntegrationTest {
    @Test
    void lesTroisProgrammesGenerentEtLisentDesDocumentsValides() throws Exception {
        // Les mêmes points d'entrée que ceux utilisés en démonstration locale.
        GenererMessageXml.main(new String[0]);
        Path xml = Path.of("message.xml");
        assertTrue(Files.size(xml) > 0, "message.xml doit être généré");

        LireMessageXml.main(new String[0]);
        Message message = (Message) JAXBContext.newInstance(Message.class)
                .createUnmarshaller().unmarshal(xml.toFile());
        assertEquals("mohamed", message.getFrom());
        assertEquals("ali", message.getTo());
        assertEquals("hello", message.getText());
        assertFalse(message.isNew(), "Le champ @XmlTransient ne doit pas être sérialisé");

        GenererSchema.main(new String[0]);
        Path xsd = Path.of("message.xsd");
        assertTrue(Files.size(xsd) > 0, "message.xsd doit être généré");

        // La validation XSD prouve plus que la seule présence de fichiers XML.
        SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(xsd.toFile())
                .newValidator()
                .validate(new StreamSource(xml.toFile()));
    }
}
