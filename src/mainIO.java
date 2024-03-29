import kuchen.Kuchen;
import kuchenImp.KuchenImp;
import kuchenImp.ObstkuchenImp;
import verwaltungsImp.verkaufsAutomat;
import verwaltungsImp.HerstellerImp;
import kuchen.Allergen;
import io.Serializer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class mainIO {
    private static final String[] kuchenTyp = {"Obstkuchen", "Kremkuchen", "Obsttorte"};
    private static final String[] obstSorte = {"Apfel", "Erdbeere", "Pflaume", "Kirsche", "Pfirsich"};
    private static final String[] kremSorte = {"Sahne", "Butter", "Schoko", "Haselnuss", "Mascarapone"};
    private static final String[] herstellerListe = {"Jannik", "Xeni", "Foo", "Bar", "Tante Emma"};
    public static void main(String[] args) {
        verkaufsAutomat vk = new verkaufsAutomat(5);

        vk.addHersteller("Jannik");
        vk.addHersteller("Xeni");
        vk.addHersteller("Foo");
        vk.addHersteller("Bar");
        vk.addHersteller("Tante Emma");

        createKuchenRandom(vk, kuchenTyp[getRandomInt(0, kuchenTyp.length - 1)]);
        createKuchenRandom(vk, kuchenTyp[getRandomInt(0, kuchenTyp.length - 1)]);
        createKuchenRandom(vk, kuchenTyp[getRandomInt(0, kuchenTyp.length - 1)]);
        createKuchenRandom(vk, kuchenTyp[getRandomInt(0, kuchenTyp.length - 1)]);
        createKuchenRandom(vk, kuchenTyp[getRandomInt(0, kuchenTyp.length - 1)]);

        Serializer.serializer("vk.txt", vk);

        verkaufsAutomat vk2 = (verkaufsAutomat) Serializer.deserialize("vk.txt");
        Collection<KuchenImp> lager = vk2.readKuchen();
        for (KuchenImp k : lager) {
            System.out.println(k);
        }
    }

    public static void createKuchenRandom(verkaufsAutomat vk, String kuchenTypString){
        vk.create(
                kuchenTypString,
                new HerstellerImp(herstellerListe[getRandomInt(0, herstellerListe.length - 1)]),
                BigDecimal.valueOf(getRandomInt(2,10)),
                getRandomInt(300, 500),
                Duration.ofDays(getRandomInt(1,14)),
                generateAllergene(),
                (kuchenTypString.equals("Obstkuchen") || kuchenTypString.equals("Obsttorte")) ? obstSorte[getRandomInt(0, obstSorte.length - 1)] : null,
                (kuchenTypString.equals("Kremkuchen") || kuchenTypString.equals("Obsttorte")) ? kremSorte[getRandomInt(0, kremSorte.length - 1)] : null
        );
    }

    private static int getRandomInt(int min, int max){
        // https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private static List<Allergen> generateAllergene(){
        List<Allergen> set = new ArrayList<Allergen>();
        for (Allergen x : EnumSet.allOf(Allergen.class)){
            if (getRandomInt(0, 100) > 66)
                set.add(x);
        }
        return set;
    }
}
