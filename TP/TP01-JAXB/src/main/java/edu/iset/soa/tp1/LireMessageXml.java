package edu.iset.soa.tp1;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public final class LireMessageXml {
    public static void main(String[] args) throws Exception {
        Unmarshaller reader = JAXBContext.newInstance(Message.class).createUnmarshaller();
        Message message = (Message) reader.unmarshal(new File("message.xml"));
        System.out.println("From = " + message.getFrom());
        System.out.println("To = " + message.getTo());
        System.out.println("Text = " + message.getText());
        System.out.println("isNew = " + message.isNew()); // non sérialisé, valeur par défaut false
    }
}
