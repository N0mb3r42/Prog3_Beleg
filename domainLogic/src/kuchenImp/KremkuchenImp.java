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
import java.util.List;

public class KremkuchenImp extends KuchenImp implements Kremkuchen, Serializable {
    private final String Kremsorte;

    public KremkuchenImp(int fachnummer, Date InspectionDate, HerstellerImp hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit, List<Allergen> allergene, String Kremsorte) {
        super(fachnummer, InspectionDate, hersteller, preis, naehrwert, haltbarkeit, allergene);
        this.Kremsorte = Kremsorte;
        this.KuchenTyp = "Kremkuchen";
    }

    @Override
    public String toString() {
        return "Fachnummer: " + this.Fachnummer +
                " | KuchenTyp: " + this.KuchenTyp +
                " | Hersteller: " + this.Hersteller.getName() +
                " | Preis: " + this.Preis.doubleValue() +
                " | Nährwert: " + this.Naehrwert +
                " | verbleibende Haltbarkeit: " + this.getRemainingHaltbarkeit().toDays() + ((this.getRemainingHaltbarkeit().toDays() == 1L) ? " Tag" : " Tage") +
                " | Allergen: " + this.Allergene +
                " | Kremsorte: " + this.Kremsorte +
                " | Inspektionsdatum: " + this.InspectionDate.toString();
    }

    @Override
    public String getKremsorte() {
        return String.valueOf(this.Kremsorte);
    }
}