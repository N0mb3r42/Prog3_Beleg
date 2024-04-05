import kuchen.Allergen;
import kuchen.Obstkuchen;
import kuchenImp.KremkuchenImp;
import kuchenImp.KuchenImp;
import kuchenImp.ObstkuchenImp;
import kuchenImp.ObsttorteImp;
import verwaltungsImp.HerstellerImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
public class ConsoleTest {
    private verkaufsAutomat automat;
    private ObstkuchenImp kuchen;
    private KremkuchenImp kuchen2;
    private ObsttorteImp kuchen3;
    private  Date todayDate;
    private  Date yesterdayDate;

    @BeforeEach
    void setUp() {
        this.automat = new verkaufsAutomat(2);
        this.kuchen = new ObstkuchenImp(1, new Date(), new HerstellerImp("Jannik"), new BigDecimal("4.50"), 365, Duration.ofDays(7), List.of(Allergen.Erdnuss), "Erddbeere1");
        this.kuchen2 = new KremkuchenImp(2, new Date(), new HerstellerImp("Alice"), new BigDecimal("4.50"), 365, Duration.ofDays(7),List.of(Allergen.Erdnuss), "Sahne2");
        this.kuchen3 = new ObsttorteImp(-1, new Date(), new HerstellerImp("Bob"), new BigDecimal("4.50"), 365, Duration.ofDays(7),List.of(Allergen.Erdnuss, Allergen.Sesamsamen), "Sahne", "Apfel");
        this.todayDate = new Date();
        this.yesterdayDate = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
    }
    @AfterEach
    void tearDown() {
        System.out.println("_______________________________");
    }

}
