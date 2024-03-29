import kuchen.Allergen;
import kuchen.Obstkuchen;
import kuchenImp.KremkuchenImp;
import kuchenImp.ObstkuchenImp;
import kuchenImp.ObsttorteImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class ObsttorteImpTest {
    private ObsttorteImp torte;

    @BeforeEach
    void setup(){
        long unixstamp  = 1711732581000L;
        Date date = new Date();
        date.setTime(unixstamp);
        this.torte = new ObsttorteImp(
                -1,
                date,
                new HerstellerImp("Alice"),
                BigDecimal.valueOf(4),
                300,
                Duration.ofDays(2),
                List.of(Allergen.Erdnuss),
                "Sahne",
                "Apfel"
        );
    }

    @AfterEach
    void teardown(){
        System.out.println("_______________________________");
    }
    @Test
    void toStringTest(){
        String kuchenString = "" +
                "Fachnummer: -1 | " +
                "KuchenTyp: Obsttorte | " +
                "Hersteller: Alice | " +
                "Preis: 4 | " +
                "Nährwert: 300 | " +
                "Haltbarkeit: PT48H | " +
                "Allergen: [Erdnuss] | " +
                "Kremsorte: Sahne | " +
                "Obstsorte: Apfel | " +
                "Inspektionsdatum: Fri Mar 29 18:16:21 CET 2024";
        assertEquals(kuchenString, this.torte.toString());
        System.out.println("TEST: toStringTest WAS SUCCESSFUL");
    }

    @Test
    void getKremsorte(){
        assertEquals("Sahne", this.torte.getKremsorte());
        System.out.println("TEST: getKremsorte WAS SUCCESSFUL");
    }

    @Test
    void getObstsorte(){
        assertEquals("Apfel", this.torte.getObstsorte());
        System.out.println("TEST: getKremsorte WAS SUCCESSFUL");
    }

}
