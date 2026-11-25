package edu.iset.soa.tp1;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"to", "from"})
public class Message {
    private String from;
    private String to;
    private String text;
    private boolean isNew;

    public Message() { }
    public Message(String from, String to, String text, boolean isNew) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.isNew = isNew;
    }
    @XmlElement(name = "emetteur", required = true)
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    @XmlElement(name = "destinataire", required = true)
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    @XmlAttribute(name = "text")
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    @XmlTransient
    public boolean isNew() { return isNew; }
    public void setNew(boolean value) { isNew = value; }
}
