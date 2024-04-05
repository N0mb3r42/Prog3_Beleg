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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class KuchenImpTest {
    private KuchenImp torte;

    @BeforeEach
    void setup(){
        long unixstamp  = 1711732581000L;
        Date date = new Date();
        date.setTime(unixstamp);
        this.torte = new KuchenImp(
                -1,
                date,
                new HerstellerImp("Alice"),
                BigDecimal.valueOf(4),
                300,
                Duration.ofDays(2),
                List.of(Allergen.Erdnuss)
        );
    }

    @AfterEach
    void teardown(){
        System.out.println("_______________________________");
    }
    @Test
    void toStringTest(){
        String kuchenString =
                "Fachnummer: -1 | " +
                "KuchenTyp: Kuchen | " +
                "Hersteller: Alice | " +
                "Preis: 4.0 | " +
                "Nährwert: 300 | " +
                "verbleibende Haltbarkeit: 2 Tage | " +
                "Allergen: [Erdnuss] | " +
                "Inspektionsdatum: Fri Mar 29 18:16:21 CET 2024";
        assertEquals(kuchenString, this.torte.toString());
        System.out.println("TEST: toStringTest WAS SUCCESSFUL");
    }

}
