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
public class KremkuchenImpTest {
    private KremkuchenImp kuchen;

    @BeforeEach
    void setup(){
        long unixstamp  = 1711732581000L;
        Date date = new Date();
        date.setTime(unixstamp);
        this.kuchen = new KremkuchenImp(
                -1,
                date,
                new HerstellerImp("Alice"),
                BigDecimal.valueOf(4),
                300,
                Duration.ofDays(2),
                List.of(Allergen.Erdnuss),
                "Sahne"
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
                "KuchenTyp: Kremkuchen | " +
                "Hersteller: Alice | " +
                "Preis: 4 | " +
                "Nährwert: 300 | " +
                "verbleibende Haltbarkeit: 2 Tage | " +
                "Allergen: [Erdnuss] | " +
                "Kremsorte: Sahne | " +
                "Inspektionsdatum: Fri Mar 29 18:16:21 CET 2024";
        assertEquals(kuchenString, this.kuchen.toString());
        System.out.println("TEST: toStringTest WAS SUCCESSFUL");
    }

    @Test
    void getKremsorte(){
        assertEquals("Sahne", this.kuchen.getKremsorte());
        System.out.println("TEST: getKremsorte WAS SUCCESSFUL");
    }
}
