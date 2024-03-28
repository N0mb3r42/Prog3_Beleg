package kuchenImp;

import kuchen.Allergen;
import kuchen.Kremkuchen;
import verwaltungsImp.HerstellerImp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class KremkuchenImp implements Kremkuchen, Serializable{

    static final long serializableUID = 1L;
    private int Fachnummer;
    private Date InspectionDate;
    private final HerstellerImp Hersteller;
    private final Collection<Allergen> Allergene;
    private final int Naehrwert;
    private final Duration Haltbarkeit;
    private final String Kremsorte;
    private final BigDecimal Preis;

    public KremkuchenImp(int fachnummer, Date InspectionDate, HerstellerImp hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit, Collection<Allergen> allergene, String Kremsorte) {
        this.Fachnummer = fachnummer;
        this.InspectionDate = InspectionDate;
        this.Hersteller = hersteller;
        this.Preis = preis;
        this.Naehrwert = naehrwert;
        this.Haltbarkeit = haltbarkeit;
        this.Allergene = allergene;
        this.Kremsorte = Kremsorte;
    }
    public void setInspectionDate(Date inspectionDate) {
        InspectionDate = inspectionDate;
    }

    public void setFachnummer(int fachnummer) {
        Fachnummer = fachnummer;
    }
    public String toString(){
        return "Fachnummer: " + this.Fachnummer +
                " | Hersteller: " + this.Hersteller.getName() +
                " | Preis: " + this.Preis +
                " | Nährwert: " + this.Naehrwert +
                " | Haltbarkeit: " + this.Haltbarkeit.toString() +
                " | Allergen: " + this.Allergene +
                " | Kremsorte: " + this.Kremsorte +
                " | Inspektionsdatum: " + this.InspectionDate.toString();
    }
    @Override
    public String getKremsorte() {
        return this.Kremsorte;
    }

    @Override
    public HerstellerImp getHersteller() {
        return this.Hersteller;
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return this.Allergene;
    }

    @Override
    public int getNaehrwert() {
        return this.Naehrwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        return this.Haltbarkeit;
    }

    @Override
    public BigDecimal getPreis() {
        return this.Preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return this.InspectionDate;
    }

    @Override
    public int getFachnummer() {
        return this.Fachnummer;
    }
}
