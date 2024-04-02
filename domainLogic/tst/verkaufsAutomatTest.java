import kuchen.Allergen;
import kuchen.Obstkuchen;
import kuchenImp.KremkuchenImp;
import kuchenImp.KuchenImp;
import kuchenImp.ObstkuchenImp;
import kuchenImp.ObsttorteImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class verkaufsAutomatTest {
    private verkaufsAutomat automat;
    private ObstkuchenImp kuchen;
    private KremkuchenImp kuchen2;
    private ObsttorteImp kuchenZuViel;
    private  Date todayDate;
    private  Date yesterdayDate;

    @BeforeEach
    void setUp() {
        this.automat = new verkaufsAutomat(2);
        this.kuchen = new ObstkuchenImp(1, new Date(), new HerstellerImp("Jannik"), new BigDecimal("4.50"), 365, Duration.ofDays(7), List.of(Allergen.Erdnuss), "Erddbeere1");
        this.kuchen2 = new KremkuchenImp(2, new Date(), new HerstellerImp("Alice"), new BigDecimal("4.50"), 365, Duration.ofDays(7),List.of(Allergen.Erdnuss), "Sahne2");
        this.kuchenZuViel = new ObsttorteImp(-1, new Date(), new HerstellerImp("Bob"), new BigDecimal("4.50"), 365, Duration.ofDays(7),List.of(Allergen.Erdnuss, Allergen.Sesamsamen), "Sahne", "Apfel");
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

        assertFalse(this.automat.create(
                "CakeIsALie",
                kuchen.getHersteller(),
                kuchen.getPreis(),
                kuchen.getNaehrwert(),
                kuchen.getHaltbarkeit(),
                kuchen.getAllergene(),
                kuchen.getObstsorte(),
                null)); //Wenn KuchenTyp nicht existiert dann False

        assertFalse(this.automat.create(
                kuchen.getKuchenTyp(),
                kuchen.getHersteller(),
                kuchen.getPreis(),
                kuchen.getNaehrwert(),
                kuchen.getHaltbarkeit(),
                kuchen.getAllergene(),
                kuchen.getObstsorte(),
                null)); //Wenn Hersteller nicht existiert dann False
        this.automat.addHersteller(kuchen.getHersteller().getName());
        assertTrue(this.automat.create(
                kuchen.getKuchenTyp(),
                kuchen.getHersteller(),
                kuchen.getPreis(),
                kuchen.getNaehrwert(),
                kuchen.getHaltbarkeit(),
                kuchen.getAllergene(),
                kuchen.getObstsorte(),
                null)); //wenn Kuchen eingefügt, dann true

        assertFalse(this.automat.create(
                kuchen2.getKuchenTyp(),
                kuchen2.getHersteller(),
                kuchen2.getPreis(),
                kuchen2.getNaehrwert(),
                kuchen2.getHaltbarkeit(),
                kuchen2.getAllergene(),
                null,
                kuchen2.getKremsorte())); //wenn Hersteller nicht existiert, dann False
        this.automat.addHersteller(kuchen2.getHersteller().getName());
        assertTrue(this.automat.create(
                kuchen2.getKuchenTyp(),
                kuchen2.getHersteller(),
                kuchen2.getPreis(),
                kuchen2.getNaehrwert(),
                kuchen2.getHaltbarkeit(),
                kuchen2.getAllergene(),
                null,
                kuchen2.getKremsorte())); //wenn Kuchen eingefügt, dann true

        this.automat.addHersteller(kuchenZuViel.getHersteller().getName());
        assertFalse(this.automat.create(
                kuchenZuViel.getKuchenTyp(),
                kuchenZuViel.getHersteller(),
                kuchenZuViel.getPreis(),
                kuchenZuViel.getNaehrwert(),
                kuchenZuViel.getHaltbarkeit(),
                kuchenZuViel.getAllergene(),
                kuchenZuViel.getObstsorte(),
                kuchenZuViel.getKremsorte())); //wenn Kapazität erreicht, dann kein Einfügen
        assertEquals(this.kuchen.toString(), this.automat.readKuchen(this.kuchen.getFachnummer()).toString()); //wenn Kuchen eingefügt, dann Kuchen in Liste
        assertEquals(this.kuchen2.toString(), this.automat.readKuchen(this.kuchen2.getFachnummer()).toString()); //wenn Kuchen eingefügt, dann Kuchen in Liste
        System.out.println("TEST: create WAS SUCCESSFUL");

    }

    @Test
    void delete() {
        assertEquals(0, this.automat.readKuchen().size()); //wenn verwaltungsImpl.verkaufsAutomat erzeugt, dann liste leer
        assertFalse(this.automat.delete(1)); //wenn, nicht vergeben Fachnummer, dann false
        this.automat.addHersteller(kuchen.getHersteller().getName());
        this.automat.addHersteller(kuchen2.getHersteller().getName());
        this.automat.addHersteller(kuchenZuViel.getHersteller().getName());

        this.automat.create(
                kuchen.getKuchenTyp(),
                kuchen.getHersteller(),
                kuchen.getPreis(),
                kuchen.getNaehrwert(),
                kuchen.getHaltbarkeit(),
                kuchen.getAllergene(),
                kuchen.getObstsorte(),
                null);
        assertEquals(1, this.automat.readKuchen().size()); //wenn ein Kuchen eingefügt, dann Liste Länge +1
        assertEquals(this.kuchen.toString(), this.automat.readKuchen(1).toString());//wenn ein Kuchen eingefügt, dann Kuchen in Liste

        assertTrue(this.automat.delete(1)); //wenn ein Kuchen eingefügt, dann entfernen(1) -> true
        assertEquals(0, this.automat.readKuchen().size()); //wenn ein Kuchen eingefügt und dann entfernt, dann Liste Länge 0
        assertNull(this.automat.readKuchen(1)); //wenn ein Kuchen entfernt, dann dieser Kuchen nicht mehr in Liste

        System.out.println("TEST: delete WAS SUCCESSFULL");
    }

    @Test
    void update() {
        this.kuchen.setInspectionDate(this.yesterdayDate); //Set Inspektionsdatum auf gestern, um Änderung sehen zu können
        this.automat.addHersteller(kuchen.getHersteller().getName());
        this.automat.addHersteller(kuchen2.getHersteller().getName());
        this.automat.addHersteller(kuchenZuViel.getHersteller().getName());

        assertNull(this.automat.readKuchen(1)); //wenn verwaltungsImpl.verkaufsAutomat erzeugt, dann null für Fachnummer
        assertTrue(this.automat.create(
                kuchen.getKuchenTyp(),
                kuchen.getHersteller(),
                kuchen.getPreis(),
                kuchen.getNaehrwert(),
                kuchen.getHaltbarkeit(),
                kuchen.getAllergene(),
                kuchen.getObstsorte(),
                null)); // create Kuchen = True
        assertNotNull(this.automat.readKuchen(1)); //Kuchen ist in Fachnummer 1
        Date before = this.automat.readKuchen(1).getInspektionsdatum();
        assertTrue(this.automat.update(1)); //wenn Update erfolgreich dann True
        assertFalse(this.automat.update(2)); // false, wenn Fach ist leer
        Date after = this.automat.readKuchen(1).getInspektionsdatum();
        assertNotEquals(before, after); //Inspektionsdatum wurde geändert → Bei Test auf heutiges Datum, schlägt manchmal fehl, obwohl richtig
        //TODO Fix Date Assert... Its can fail when Programm is perfectly fast
        System.out.println("TEST: update WAS SUCCESSFULL");

    }

    @Test
    void readKuchen() {
        assertEquals(0, this.automat.readKuchen().size()); //wenn verwaltungsImpl.verkaufsAutomat erzeugt, dann liste leer
        this.automat.addHersteller(kuchen.getHersteller().getName());
        this.automat.addHersteller(kuchen2.getHersteller().getName());
        this.automat.addHersteller(kuchenZuViel.getHersteller().getName());
        assertTrue(this.automat.create(
                kuchen.getKuchenTyp(),
                kuchen.getHersteller(),
                kuchen.getPreis(),
                kuchen.getNaehrwert(),
                kuchen.getHaltbarkeit(),
                kuchen.getAllergene(),
                kuchen.getObstsorte(),
                null));// wenn Einfügen erfolgreich dann True
        assertEquals(1, this.automat.readKuchen().size()); //wenn verwaltungsImpl.verkaufsAutomat erzeugt, dann liste leer
        assertTrue(this.automat.create(
                kuchen2.getKuchenTyp(),
                kuchen2.getHersteller(),
                kuchen2.getPreis(),
                kuchen2.getNaehrwert(),
                kuchen2.getHaltbarkeit(),
                kuchen2.getAllergene(),
                null,
                kuchen2.getKremsorte())); //wenn Einfügen erfolgreich dann True
        assertFalse(this.automat.create(
                kuchenZuViel.getKuchenTyp(),
                kuchenZuViel.getHersteller(),
                kuchenZuViel.getPreis(),
                kuchenZuViel.getNaehrwert(),
                kuchenZuViel.getHaltbarkeit(),
                kuchenZuViel.getAllergene(),
                kuchenZuViel.getObstsorte(),
                kuchenZuViel.getKremsorte()));// wenn maximaler Füllstand erreicht dann False
        System.out.println("TEST for readKuchen() was SUCCESSFULL");

        assertEquals(this.automat.getAnzahlFaecher(), this.automat.readKuchen().size()); //wenn maximalie Kuchen eingefügt, dann Liste Länge maxFüllstand
        assertEquals(this.kuchen.toString(), this.automat.readKuchen(1).toString());//wenn kuchen eingfügt, dann diese kuchen in liste
        assertEquals(this.kuchen2.toString(), this.automat.readKuchen(2).toString());//wenn kuchen eingfügt, dann diese kuchen in liste
        System.out.println("TEST for readKuchen(Integer) was SUCCESSFULL");

        Collection<KuchenImp> ObstkuchenListe = this.automat.readKuchen("Obstkuchen");
        Collection<KuchenImp> KremkuchenListe = this.automat.readKuchen("Kremkuchen");
        Collection<KuchenImp> ObsttortenListe = this.automat.readKuchen("Obsttorte");
        assertEquals(1, ObstkuchenListe.size());
        assertEquals(1, KremkuchenListe.size());
        assertEquals(0, ObsttortenListe.size());

        assertEquals(ObstkuchenListe.iterator().next().getHersteller().getName(), "Jannik");
        assertEquals(KremkuchenListe.iterator().next().getHersteller().getName(), "Alice");
        System.out.println("TEST for readKuchen(String) was SUCCESSFULL");
        System.out.println("TEST: readKuchen WAS SUCCESSFULL");
    }

    @Test
    void addHersteller(){
        fail("Finish the test!!");
    }

    @Test
    void deleteHersteller(){
        fail("Finish the Test!!");
    }
}