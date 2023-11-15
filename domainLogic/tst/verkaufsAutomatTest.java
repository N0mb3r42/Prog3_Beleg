import kuchen.Allergen;
import kuchenImp.ObstkuchenImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class verkaufsAutomatTest {
    private verkaufsAutomat automat;
    private ObstkuchenImp kuchen;
    private ObstkuchenImp kuchen2;
    private ObstkuchenImp kuchenZuViel;
    private  Date todayDate;
    private  Date yesterdayDate;

    @BeforeEach
    void setUp() {
        this.automat = new verkaufsAutomat(2);
        this.kuchen = new ObstkuchenImp(-1, new Date(), new HerstellerImp("Alice"), new BigDecimal("4.50"), 365, Duration.ofDays(7), Set.of(Allergen.Erdnuss), "Erddbeere");
        this.kuchen2 = new ObstkuchenImp(-1, new Date(), new HerstellerImp("Alice"), new BigDecimal("4.50"), 365, Duration.ofDays(7),Set.of(Allergen.Erdnuss), "Erddbeere");
        this.kuchenZuViel = new ObstkuchenImp(-1, new Date(), new HerstellerImp("Alice"), new BigDecimal("4.50"), 365, Duration.ofDays(7),Set.of(Allergen.Erdnuss), "Erddbeere");
        this.todayDate = new Date();
        this.yesterdayDate = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
    }

    @AfterEach
    void tearDown() {
        System.out.println("_______________________________");
    }

    @Test
    void getAnzahlFaecher() {
        assertEquals(this.automat.getAnzahlFaecher(), 2);
        System.out.println("TEST: getAnzahlFaecher WAS SUCCESSFUL");
    }

    @Test
    void create() {
        assertTrue(this.automat.create(this.kuchen)); //wenn Kuchen eingefügt, dann true
        assertFalse(this.automat.create(null)); //wenn kuchen==null, dann false
        assertTrue(this.automat.create(this.kuchen2)); //wenn Kuchen eingefügt, dann true
        assertFalse(this.automat.create(this.kuchenZuViel)); //wenn Kapazität erreicht, dann kein Einfügen
        assertEquals(this.kuchen, this.automat.read().get(1)); //wenn Kuchen eingefügt, dann Kuchen in Liste
        assertEquals(this.kuchen2, this.automat.read().get(2)); //wenn Kuchen eingefügt, dann Kuchen in Liste
        System.out.println("TEST: create WAS SUCCESSFUL");

    }

    @Test
    void delete() {
        assertEquals(0, this.automat.getLager().size()); //wenn verwaltungsImpl.verkaufsAutomat erzeugt, dann liste leer
        assertFalse(this.automat.delete(1)); //wenn, nicht vergeben Fachnummer, dann false

        this.automat.create(this.kuchen);
        assertEquals(1, this.automat.getLager().size()); //wenn ein Kuchen eingefügt, dann Liste Länge +1
        assertEquals(this.kuchen, this.automat.read().get(1));//wenn ein Kuchen eingefügt, dann Kuchen in Liste

        assertTrue(this.automat.delete(1)); //wenn ein Kuchen eingefügt, dann entfernen(1) -> true
        assertEquals(0, this.automat.getLager().size()); //wenn ein Kuchen eingefügt und dann entfernt, dann Liste Länge 0
        assertNull(this.automat.read().get(1)); //wenn ein Kuchen entfernt, dann dieser Kuchen nicht mehr in Liste

        System.out.println("TEST: delete WAS SUCCESSFULL");
    }

    @Test
    void update() {
        this.kuchen.setInspectionDate(this.yesterdayDate); //Set Inspektionsdatum auf gestern, um Änderung sehen zu können
        assertNull(this.automat.read().get(1)); //wenn verwaltungsImpl.verkaufsAutomat erzeugt, dann null für Fachnummer
        assertTrue(this.automat.create(kuchen)); // create Kuchen = True
        assertNotNull(this.automat.read().get(1)); //Kuchen ist in Fachnummer 1
        Date before = this.automat.read().get(1).getInspektionsdatum();
        assertTrue(this.automat.update(1)); //wenn Update erfolgreich dann True
        assertFalse(this.automat.update(2)); // false, wenn Fach ist leer
        Date after = this.automat.read().get(1).getInspektionsdatum();
        assertNotEquals(before, after); //Inspektionsdatum wurde geändert -> Bei Test auf heutiges Datum, schlägt manchmal fehl, obwohl richtig
        System.out.println("TEST: update WAS SUCCESSFULL");
    }

    @Test
    void read() {
        assertEquals(0, this.automat.getLager().size()); //wenn verwaltungsImpl.verkaufsAutomat erzeugt, dann liste leer
        assertTrue(this.automat.create(this.kuchen));// wenn Einfügen erfolgreich dann True
        assertEquals(1, this.automat.getLager().size()); //wenn verwaltungsImpl.verkaufsAutomat erzeugt, dann liste leer
        assertTrue(this.automat.create(this.kuchen2)); //wenn Einfügen erfolgreich dann True
        assertFalse(this.automat.create((this.kuchenZuViel)));// wenn maximaler Füllstand erreicht dann False
        assertEquals(this.automat.getAnzahlFaecher(), this.automat.getLager().size()); //wenn maximalie Kuchen eingefügt, dann Liste Länge maxFüllstand
        assertEquals(this.kuchen, this.automat.read().get(1));//wenn kuchen eingfügt, dann diese kuchen in liste
        assertEquals(this.kuchen2, this.automat.read().get(2));//wenn kuchen eingfügt, dann diese kuchen in liste
        System.out.println("TEST: read WAS SUCCESSFULL");
    }
}