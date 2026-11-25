package edu.iset.soa.tp1;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.File;

public final class GenererMessageXml {
    public static void main(String[] args) throws Exception {
        Message message = new Message("mohamed", "ali", "hello", true);
        JAXBContext context = JAXBContext.newInstance(Message.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File target = new File("message.xml");
        marshaller.marshal(message, target);
        System.out.println("XML généré : " + target.getAbsolutePath());
    }
}
