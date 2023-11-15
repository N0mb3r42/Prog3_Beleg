import kuchenImp.ObstkuchenImp;
import verwaltungsImp.verkaufsAutomat;
import verwaltungsImp.HerstellerImp;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class main{
    public static void main(String[] args) {
        verkaufsAutomat automat = new verkaufsAutomat(2);
        ObstkuchenImp kuchen = new ObstkuchenImp(-1, new Date(), new HerstellerImp("Alice"), new BigDecimal("4.50"), 365, Duration.ofDays(7),Set.of(Allergen.Erdnuss), "Erddbeere");
        automat.create(kuchen);
        System.out.println(automat.read(1));
    }
}
