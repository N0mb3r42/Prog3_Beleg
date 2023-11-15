package verwaltungsImp;
import verwaltung.Hersteller;

public class HerstellerImp implements Hersteller {
    private final String name;

    public HerstellerImp(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
