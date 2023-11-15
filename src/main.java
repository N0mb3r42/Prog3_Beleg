import kuchenImp.ObstkuchenImp;
import verwaltungsImp.verkaufsAutomat;
import verwaltungsImp.HerstellerImp;
import kuchen.Allergen;

import viewControl.Console;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class main{
    public static void main(String[] args) {
        verkaufsAutomat automat = new verkaufsAutomat(10);
        Console cli = new Console(automat);
        cli.execute();

    }
}
