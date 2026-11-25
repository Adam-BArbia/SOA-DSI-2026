package edu.iset.soa.tp3.entity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "compte")
@XmlAccessorType(XmlAccessType.FIELD)
public class Compte {
    private long code;
    private double solde;
    private Date dateCreation;
    public Compte() { }
    public Compte(long code, double solde, Date dateCreation) {
        this.code = code; this.solde = solde; this.dateCreation = dateCreation;
    }
    public long getCode() { return code; }
    public void setCode(long code) { this.code = code; }
    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }
    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
}
