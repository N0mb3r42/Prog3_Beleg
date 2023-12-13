package kuchenImp;

import kuchen.Allergen;
import kuchen.Obstkuchen;
import verwaltungsImp.HerstellerImp;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public class ObstkuchenImp implements Obstkuchen {

    private int Fachnummer;
    private Date InspectionDate;
    private final HerstellerImp Hersteller;
    private final Collection<Allergen> Allergene;
    private final int Naehrwert;
    private final Duration Haltbarkeit;
    private final String Obstsorte;
    private final BigDecimal Preis;
    public ObstkuchenImp(int fachnummer, Date InspectionDate, HerstellerImp hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit, Collection<Allergen> allergene, String obstsorte) {
        this.Fachnummer = fachnummer;
        this.InspectionDate = InspectionDate;
        this.Hersteller = hersteller;
        this.Preis = preis;
        this.Naehrwert = naehrwert;
        this.Haltbarkeit = haltbarkeit;
        this.Allergene = allergene;
        this.Obstsorte = obstsorte;
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
                " | Obstsorte: " + this.Obstsorte;
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
    public String getObstsorte() {
        return this.Obstsorte;
    }

    @Override
    public BigDecimal getPreis() {
        return this.Preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return this.InspectionDate;
    }
    protected void setInspektionsdatum(Date date){
        this.InspectionDate = date;
    }

    @Override
    public int getFachnummer() {
        return this.Fachnummer;
    }
}
