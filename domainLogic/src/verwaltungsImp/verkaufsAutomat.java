package verwaltungsImp;

import kuchenImp.ObstkuchenImp;
import java.util.Date;
import java.util.HashMap;

public class verkaufsAutomat {
    private final int anzahlFaecher;
    private HashMap<Integer, ObstkuchenImp> lager;

    public int getAnzahlFaecher() {
        return anzahlFaecher;
    }

    public HashMap<Integer, ObstkuchenImp> getLager() {
        return lager;
    }

    public verkaufsAutomat(int anzahlFaecher){
        this.anzahlFaecher = anzahlFaecher;
        this.lager = new HashMap<Integer, ObstkuchenImp>();
    }
    public Boolean create(ObstkuchenImp inputKuchen){
        if (inputKuchen == null){
            return false;
        }
        int freiersFach = this.findNextFreeSlot();
        if (freiersFach == 0)
            return false;
        inputKuchen.setFachnummer(freiersFach);
        this.lager.put(freiersFach, inputKuchen);
        return true;
    }
    private int findNextFreeSlot(){
        for (int i = 1; i <= this.anzahlFaecher; i++){
            if (!this.lager.containsKey(i))
                return i;
        }
        return 0;
    }
    public boolean delete(int fachnummer){
        if (this.lager.get(fachnummer) == null){
            return false;
        }
        this.lager.remove(fachnummer);
        return true;
    }

    public boolean update(int fachnummer){
        ObstkuchenImp kuchen = this.lager.get(fachnummer);
        if(kuchen == null){
            return false;
        }
        kuchen.setInspectionDate(new Date());
        return true;
    }
    public HashMap<Integer, ObstkuchenImp> read(){
        return this.lager;
    }

}
