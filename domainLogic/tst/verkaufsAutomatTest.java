import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
class VerkaufsAutomatTest {
    private verkaufsAutomat automat;
    private ObstkuchenImp kuchen;
    @BeforeEach
    void setUp() {
        this.automat = new verkaufsAutomat(2);
        this.kuchen = new ObstkuchenImp(-1, new Date());
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
        assertEquals(1, this.automat.create(kuchen).getFachnummer());
        System.out.println("TEST: create WAS SUCCESSFUL");
    }

    @Test
    void delete() {
        int fachnummer = this.automat.create(kuchen).getFachnummer();
        this.automat.delete(1);
        ObstkuchenImp after_delete = this.automat.read(fachnummer);
        assertNull(after_delete);
        System.out.println("TEST: delete WAS SUCCESSFULL");
    }

    @Test
    void update() {
        this.kuchen.setInspectionDate(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
        int fachnummer = this.automat.create(kuchen).getFachnummer();

        Date before = this.automat.read(fachnummer).getInspektionsdatum();

        assertTrue(this.automat.update(fachnummer));

        Date after = this.automat.read(fachnummer).getInspektionsdatum();
        assertNotEquals(before, after);
        System.out.println("TEST: update WAS SUCCESSFULL");
    }

    @Test
    void read() {
        int fachnummer = this.automat.create(kuchen).getFachnummer();
        assertEquals(this.kuchen, this.automat.read(fachnummer));
        System.out.println("TEST: read WAS SUCCESSFULL");
    }
}