package edu.iset.soa.tp1;

import jakarta.xml.bind.JAXBContext;
import org.junit.jupiter.api.Test;
import java.io.StringReader;
import java.io.StringWriter;
import static org.junit.jupiter.api.Assertions.*;

class MessageJaxbTest {
  @Test void roundTripAndTransient() throws Exception {
    var context = JAXBContext.newInstance(Message.class);
    var writer = new StringWriter();
    context.createMarshaller().marshal(new Message("mohamed", "ali", "hello", true), writer);
    var xml = writer.toString();
    assertTrue(xml.contains("<emetteur>mohamed</emetteur>"));
    assertFalse(xml.contains("isNew"));
    var result = (Message) context.createUnmarshaller().unmarshal(new StringReader(xml));
    assertEquals("ali", result.getTo());
    assertEquals("hello", result.getText());
    assertFalse(result.isNew());
  }
}
