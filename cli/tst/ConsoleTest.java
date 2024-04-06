import kuchen.Allergen;
import kuchen.Obstkuchen;
import kuchenImp.KremkuchenImp;
import kuchenImp.KuchenImp;
import kuchenImp.ObstkuchenImp;
import kuchenImp.ObsttorteImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;
import viewControl.Console;
import io.Serializer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ConsoleTest {
    @Spy
    private Console spyConsole;
    private Console console;
    @Spy
    private verkaufsAutomat spyAutomat;
    private verkaufsAutomat automat;
    @Spy
    private Serializer s;

    @BeforeEach
    void setUp() {
        this.s = Mockito.spy(Serializer.class);
        this.automat = new verkaufsAutomat(2);
        this.spyAutomat = Mockito.spy(new verkaufsAutomat(2));
        this.console = new Console(this.automat, this.s);
        this.spyConsole = Mockito.spy(new Console(this.spyAutomat, this.s));
    }

    @Test
    void kuchenCreateTest(){
        String returnValue = this.spyConsole.inputHandling(":c");
        assertEquals("Switch to Create Mode\n", returnValue);
        returnValue = this.spyConsole.inputHandling("Alice");
        assertEquals("Hersteller: Alice added!", returnValue);
        verify(this.spyAutomat, times(1)).addHersteller("Alice");

        returnValue = this.spyConsole.inputHandling("Kremkuchen Alice 4,50 386 36 Gluten,Erdnuss Butter");
        assertEquals("Kuchen wurde eingefügt!", returnValue);
        verify(this.spyAutomat, times(1)).findNextFreeSlot();

        System.out.println("Kuchen Create Test was SUCCESSFULL!");
    }

    @Test
    void herstellerCreateTest(){
        String returnValue = this.spyConsole.inputHandling(":c");
        assertEquals("Switch to Create Mode\n", returnValue);
        returnValue = this.spyConsole.inputHandling("Alice");
        assertEquals("Hersteller: Alice added!", returnValue);
        verify(this.spyAutomat, times(1)).addHersteller("Alice");
        System.out.println("Hersteller Create Test was SUCCESSFULL!");
    }

    @Test
    void herstellerReadTest(){
        String returnValue = this.spyConsole.inputHandling(":c");
        assertEquals("Switch to Create Mode\n", returnValue);
        returnValue = this.spyConsole.inputHandling("Alice");
        assertEquals("Hersteller: Alice added!", returnValue);
        verify(this.spyAutomat, times(1)).addHersteller("Alice");

        returnValue = this.spyConsole.inputHandling(":r");
        assertEquals("Switch to Read Mode\n", returnValue);
        returnValue = this.spyConsole.inputHandling("hersteller");
        assertEquals("Anzeige aller Hersteller mit Anzahl ihrer Kuchen:\nAlice: 0\n", returnValue);
        verify(this.spyAutomat, times(1)).getHerstellerMitKuchenAnzahl();
        System.out.println("Hersteller Read Test was SUCCESSFULL!");
    }

    @Test
    void saveJOSTest(){
        String returnValue = console.inputHandling(":c");
        assertEquals("Switch to Create Mode\n", returnValue);
        returnValue = console.inputHandling("Alice_Persitence");
        assertEquals("Hersteller: Alice_Persitence added!", returnValue);
        //verify(this.automat, times(1)).addHersteller("Alice_Persitence");

        returnValue = console.inputHandling(":p");
        assertEquals("Switch to Persitence Mode\n", returnValue);
        returnValue = console.inputHandling("saveJOS JOSSaveTest.txt");
        assertEquals("Saved with JOS", returnValue);
        verify(this.s, times(1)).serializer("JOSSaveTest.txt", this.automat);
        System.out.println("saveJOSTest was SUCCESSFULL!");

    }

    @Test
    void loadJOSTest(){
        String returnValue = console.inputHandling(":c");
        assertEquals("Switch to Create Mode\n", returnValue);
        returnValue = console.inputHandling("Alice_Persitence");
        assertEquals("Hersteller: Alice_Persitence added!", returnValue);
        //verify(this.automat, times(1)).addHersteller("Alice_Persitence");

        returnValue = console.inputHandling(":p");
        assertEquals("Switch to Persitence Mode\n", returnValue);
        returnValue = console.inputHandling("saveJOS JOSLoadTest.txt");
        assertEquals("Saved with JOS", returnValue);
        verify(this.s, times(1)).serializer("JOSLoadTest.txt", this.automat);

        verkaufsAutomat loadTestAutomat = new verkaufsAutomat(2);
        Console loadTestconsole = new Console(loadTestAutomat, this.s);
        returnValue = loadTestconsole.inputHandling(":p");
        assertEquals("Switch to Persitence Mode\n", returnValue);
        returnValue = loadTestconsole.inputHandling("loadJOS JOSLoadTest.txt");
        assertEquals("Loaded with JOS", returnValue);
        verify(this.s, times(1)).serializer("JOSLoadTest.txt", this.automat);

        returnValue = loadTestconsole.inputHandling(":r");
        assertEquals("Switch to Read Mode\n", returnValue);
        returnValue = loadTestconsole.inputHandling("hersteller");
        assertEquals("Anzeige aller Hersteller mit Anzahl ihrer Kuchen:\nAlice_Persitence: 0\n", returnValue);
        System.out.println("loadJOSTest was SUCCESSFULL!");
    }

    @AfterEach
    void tearDown() {
        System.out.println("_______________________________");
    }
}
