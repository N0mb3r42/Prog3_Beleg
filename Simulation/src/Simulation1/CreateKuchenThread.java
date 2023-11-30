package Simulation1;

import kuchenImp.ObstkuchenImp;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class CreateKuchenThread extends Thread{

    private final String[] allergene1 = {"Erdnuss", "Gluten"};
    private final String[] allergene2 = {"Sesamsamen", "Haselnuss"};
    private final String[] allergene3 = {"Sesamsamen", "Gluten"};
    private final String[] allergene4 = {"Haselnuss", "Erdnuss"};
    private List<Allergen> parseAllergene(String[] text) {
        List<Allergen> allergene = new ArrayList<>();
        if (Objects.equals(text[0], "")) {
            return null;
        }
        for (String s : text) {
            for (Allergen a : Allergen.values()) {
                if (a.name().equals(s)) {
                    allergene.add(a);
                }
            }
        }
        return allergene;
    }
    private ArrayList<ObstkuchenImp> kuchen = new ArrayList<>(Arrays.asList(
            new ObstkuchenImp(-1, new Date(),new HerstellerImp("Alice"), new BigDecimal("4.50"), 300, Duration.ofDays(14), this.parseAllergene(allergene1), "Apfel"),
            new ObstkuchenImp(-1, new Date(),new HerstellerImp("Jannik"), new BigDecimal("3.25"), 200, Duration.ofDays(7), this.parseAllergene(allergene2), "Erdbeere"),
            new ObstkuchenImp(-1, new Date(),new HerstellerImp("Xenia"), new BigDecimal("5.00"), 500, Duration.ofDays(20), this.parseAllergene(allergene3), "Birne"),
            new ObstkuchenImp(-1, new Date(),new HerstellerImp("Marco"), new BigDecimal("6.50"), 100, Duration.ofDays(16), this.parseAllergene(allergene4), "Mango"))
    );
    public verkaufsAutomat automat;
    public CreateKuchenThread(verkaufsAutomat imput){
        this.automat = imput;
    }
    @Override
    public void run() {
        while (true) {
            Collections.shuffle(this.kuchen);
            synchronized (this.automat){
                int freeSlot = this.automat.findNextFreeSlot();
                if (freeSlot == 0){
                    continue;
                }else{
                    boolean returnvalue = this.automat.create(new ObstkuchenImp(-1, new Date(),new HerstellerImp("Alice"), new BigDecimal("4.50"), 300, Duration.ofDays(14), this.parseAllergene(allergene1), "Apfel"));
                    if (returnvalue){
                        System.out.println("Fachnummer: " + freeSlot + " | Kuchen wurde hinzugefügt | from Thread: " + Thread.currentThread().threadId());
                    }else{
                        System.out.println("Kuchen konnte nicht created werden | from Thread: " + Thread.currentThread().threadId());
                    }
                }

            }

        }
    }
}
