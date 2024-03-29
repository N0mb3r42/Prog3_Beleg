import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import verwaltungsImp.HerstellerImp;

import static org.junit.jupiter.api.Assertions.*;
public class HerstellerImpTest {
    private HerstellerImp hersteller;

    @BeforeEach
    void setup(){
        this.hersteller = new HerstellerImp("Hersteller1");
    }

    @AfterEach
    void teardown(){
        System.out.println("_______________________________");
    }
    @Test
    void getName(){
        assertEquals("Hersteller1", this.hersteller.getName());
        System.out.println("TEST: getName WAS SUCCESSFUL");
    }

}
