import kuchenImp.ObstkuchenImp;
import verwaltungsImp.verkaufsAutomat;

import java.util.Date;

public class main{
    public static void main(String[] args) {
        verkaufsAutomat automat = new verkaufsAutomat(2);
        ObstkuchenImp kuchen = new ObstkuchenImp(-1, new Date());
        automat.create(kuchen);
        System.out.println(automat.read(1));
    }
}
