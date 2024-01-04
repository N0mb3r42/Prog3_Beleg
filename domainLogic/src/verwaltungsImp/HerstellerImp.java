package verwaltungsImp;
import verwaltung.Hersteller;

import java.io.Serializable;

public class HerstellerImp implements Hersteller, Serializable {

    static final long serializableUID = 1L;
    private final String name;

    public HerstellerImp(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
