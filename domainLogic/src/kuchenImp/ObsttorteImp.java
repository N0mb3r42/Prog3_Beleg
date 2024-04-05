package kuchenImp;

import kuchen.*;
import verwaltung.Hersteller;
import verwaltungsImp.HerstellerImp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ObsttorteImp extends KuchenImp implements Obsttorte, Serializable {
    private final String Kremsorte;
    private final String Obstsorte;

    public ObsttorteImp(int fachnummer, Date InspectionDate, HerstellerImp hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit, List<Allergen> allergene, String Kremsorte, String Obstsorte) {
        super(fachnummer, InspectionDate, hersteller, preis, naehrwert, haltbarkeit, allergene);
        this.Kremsorte = Kremsorte;
        this.Obstsorte = Obstsorte;
        this.KuchenTyp = "Obsttorte";
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
                " | Obstsorte: " + this.Obstsorte +
                " | Inspektionsdatum: " + this.InspectionDate.toString();
    }

    @Override
    public String getKremsorte() {
        return this.Kremsorte;
    }
    @Override
    public String getObstsorte() {
        return this.Obstsorte;
    }
}
