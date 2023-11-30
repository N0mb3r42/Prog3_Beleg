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
    private List<ObstkuchenImp> kuchen = List.of(
            new ObstkuchenImp(-1, new Date(),new HerstellerImp("Alice"), new BigDecimal("4.50"), 300, Duration.ofDays(14), this.parseAllergene(allergene1), "Apfel"),
            new ObstkuchenImp(-1, new Date(),new HerstellerImp("Jannik"), new BigDecimal("3.25"), 200, Duration.ofDays(7), this.parseAllergene(allergene2), "Erdbeere"),
            new ObstkuchenImp(-1, new Date(),new HerstellerImp("Xenia"), new BigDecimal("5.00"), 500, Duration.ofDays(20), this.parseAllergene(allergene3), "Birne"),
            new ObstkuchenImp(-1, new Date(),new HerstellerImp("Marco"), new BigDecimal("6.50"), 100, Duration.ofDays(16), this.parseAllergene(allergene4), "Mango")
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
                if (this.automat.getAnzahlFaecher() == 0){
                    System.out.println("Maximal Anzahl an Kuchen erreicht. Bitte lösche erst einen Kuschen");
                }
                this.automat.create(this.kuchen.get(0));

            }

        }
    }
}
