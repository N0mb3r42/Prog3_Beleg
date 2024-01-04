import kuchenImp.ObstkuchenImp;
import verwaltungsImp.verkaufsAutomat;
import verwaltungsImp.HerstellerImp;
import kuchen.Allergen;
import io.Serializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;


public class mainIO {
    public static void main(String[] args) {
        verkaufsAutomat vk = new verkaufsAutomat(5);
        vk.create(createKuchenRandom());
        vk.create(createKuchenRandom());
        vk.create(createKuchenRandom());
        vk.create(createKuchenRandom());
        vk.create(createKuchenRandom());
        Serializer.serializer("vk.txt", vk);

        verkaufsAutomat vk2 = (verkaufsAutomat) Serializer.deserialize("vk.txt");
        Collection<ObstkuchenImp> lager = vk2.readKuchen();
        for (ObstkuchenImp k : lager) {
            System.out.println(k);
        }
    }

    public static ObstkuchenImp createKuchenRandom(){
        List<String> hersteller = Arrays.asList("Jannik", "Xeni", "Foo", "Bar", "Tante Emma");
        List<String> obstsorte = Arrays.asList("Apfel", "Erdbeere", "Pflaume", "Kirsche", "Pfirsich");
        List<String> preis = Arrays.asList("4.99", "3.50", "2.99", "6.00", "1.99");
        List<Integer> naehrwert = Arrays.asList(300, 299, 450, 220, 321);
        List<Integer> haltbarkeit = Arrays.asList(30, 15, 7, 8, 10);
        List<List<Allergen>> allergene = Arrays.asList(
                Arrays.asList(Allergen.Erdnuss, Allergen.Gluten),
                Arrays.asList(Allergen.Gluten, Allergen.Haselnuss),
                Arrays.asList(Allergen.Haselnuss, Allergen.Sesamsamen),
                Arrays.asList(Allergen.Sesamsamen, Allergen.Erdnuss));

        Collections.shuffle(hersteller);
        Collections.shuffle(obstsorte);
        Collections.shuffle(preis);
        Collections.shuffle(naehrwert);
        Collections.shuffle(haltbarkeit);
        Collections.shuffle(allergene);

        return new ObstkuchenImp(
                -1,
                new Date(),
                new HerstellerImp(hersteller.getFirst()),
                new BigDecimal(preis.getFirst()),
                naehrwert.getFirst(),
                Duration.ofDays(haltbarkeit.getFirst()),
                allergene.getFirst(),
                obstsorte.getFirst());
    }
}
