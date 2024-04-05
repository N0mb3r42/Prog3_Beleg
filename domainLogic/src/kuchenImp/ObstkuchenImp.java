package kuchenImp;

import kuchen.Allergen;
import kuchen.Kremkuchen;
import kuchen.Obstkuchen;
import verwaltungsImp.HerstellerImp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ObstkuchenImp extends KuchenImp implements Obstkuchen, Serializable {
    private final String Obstsorte;

    public ObstkuchenImp(int fachnummer, Date InspectionDate, HerstellerImp hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit, List<Allergen> allergene, String Obstsorte) {
        super(fachnummer, InspectionDate, hersteller, preis, naehrwert, haltbarkeit, allergene);
        this.Obstsorte = Obstsorte;
        this.KuchenTyp = "Obstkuchen";
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
                " | Obstsorte: " + this.Obstsorte +
                " | Inspektionsdatum: " + this.InspectionDate.toString();
    }

    @Override
    public String getObstsorte() {
        return String.valueOf(this.Obstsorte);
    }
}
