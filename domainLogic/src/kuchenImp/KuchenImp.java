package kuchenImp;

import kuchen.Allergen;
import kuchen.Kuchen;
import verwaltung.Verkaufsobjekt;
import verwaltungsImp.HerstellerImp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class KuchenImp implements Kuchen, Verkaufsobjekt, Serializable {
    static final long serializableUID = 1L;
    protected int Fachnummer;
    protected Date InspectionDate;
    protected final HerstellerImp Hersteller;
    protected final List<Allergen> Allergene;
    protected final int Naehrwert;
    protected final Duration Haltbarkeit;
    protected final BigDecimal Preis;
    protected String KuchenTyp;
    protected Date ErstellungsDatum;

    public KuchenImp(int fachnummer, Date InspectionDate, HerstellerImp hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit, List<Allergen> allergene) {
        this.Fachnummer = fachnummer;
        this.InspectionDate = InspectionDate;
        this.Hersteller = hersteller;
        this.Preis = preis;
        this.Naehrwert = naehrwert;
        this.Haltbarkeit = haltbarkeit;
        this.Allergene = allergene;
        this.KuchenTyp = "Kuchen";
        this.ErstellungsDatum = new Date();
    }
    public void setInspectionDate(Date inspectionDate) {
        InspectionDate = inspectionDate;
    }


    public void setFachnummer(int fachnummer) {
        Fachnummer = fachnummer;
    }

    public String toString(){
        return "Fachnummer: " + this.Fachnummer +
                " | KuchenTyp: " + this.KuchenTyp +
                " | Hersteller: " + this.Hersteller.getName() +
                " | Preis: " + this.Preis +
                " | Nährwert: " + this.Naehrwert +
                " | verbleibende Haltbarkeit: " + this.getRemainingHaltbarkeit().toDays() + ((this.getRemainingHaltbarkeit().toDays() == 1L) ? " Tag" : " Tage") +
                " | Allergen: " + this.Allergene +
                " | Inspektionsdatum: " + this.InspectionDate.toString();
    }

    public Duration getRemainingHaltbarkeit(){
        Date heute = new Date();
        Duration verbrauchteHaltbarkeit = Duration.between(this.ErstellungsDatum.toInstant(), heute.toInstant());
        return Duration.ofDays(this.Haltbarkeit.minus(verbrauchteHaltbarkeit).toDaysPart());
    }
    @Override
    public HerstellerImp getHersteller() {
        return this.Hersteller;
    }

    @Override
    public List<Allergen> getAllergene() {
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

    public String getKuchenTyp(){return this.KuchenTyp;}
}
