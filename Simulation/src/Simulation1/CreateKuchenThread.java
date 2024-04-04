package Simulation1;

import kuchenImp.KremkuchenImp;
import kuchenImp.KuchenImp;
import kuchenImp.ObstkuchenImp;
import kuchenImp.ObsttorteImp;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CreateKuchenThread extends Thread{

    private static final String[] kuchenTypList = {"Obstkuchen", "Kremkuchen", "Obsttorte"};
    private static final String[] obstSorte = {"Apfel", "Erdbeere", "Pflaume", "Kirsche", "Pfirsich"};
    private static final String[] kremSorte = {"Sahne", "Butter", "Schoko", "Haselnuss", "Mascarapone"};
    private static final String[] herstellerListe = {"Jannik", "Xeni", "Foo", "Bar", "Tante Emma"};
    private static List<Allergen> generateAllergene(){
        List<Allergen> set = new ArrayList<Allergen>();
        for (Allergen x : EnumSet.allOf(Allergen.class)){
            if (getRandomInt(0, 100) > 66)
                set.add(x);
        }
        return set;
    }
    public verkaufsAutomat automat;
    public CreateKuchenThread(verkaufsAutomat imput){
        this.automat = imput;
    }
    @Override
    public void run() {
        while (true) {
            synchronized (this.automat){
                int freeSlot = this.automat.findNextFreeSlot();
                if (freeSlot == 0){
                    continue;
                }else{
                    HerstellerImp hersteller = new HerstellerImp(herstellerListe[getRandomInt(0, herstellerListe.length - 1)]);
                    String kuchenTypString = kuchenTypList[getRandomInt(0, kuchenTypList.length-1)];
                    this.automat.addHersteller(hersteller.getName());
                    boolean returnvalue = this.automat.create(
                            kuchenTypString,
                            hersteller,
                            BigDecimal.valueOf(getRandomInt(2,10)),
                            getRandomInt(300,500),
                            Duration.ofDays(getRandomInt(1,14)),
                            generateAllergene(),
                            (kuchenTypString.equals("Obstkuchen") || kuchenTypString.equals("Obsttorte")) ? obstSorte[getRandomInt(0, obstSorte.length - 1)] : null,
                            (kuchenTypString.equals("Kremkuchen") || kuchenTypString.equals("Obsttorte")) ? kremSorte[getRandomInt(0, kremSorte.length - 1)] : null
                    );
                    if (returnvalue){
                        System.out.println("Fachnummer: " + freeSlot + "\t| Kuchen wurde hinzugefügt | from Thread: " + Thread.currentThread().threadId());
                    }else{
                        System.out.println("Kuchen konnte nicht created werden | from Thread: " + Thread.currentThread().threadId());
                    }
                }

            }

        }
    }
    private static int getRandomInt(int min, int max){
        // https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
