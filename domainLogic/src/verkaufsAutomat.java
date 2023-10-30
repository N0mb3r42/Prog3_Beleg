import java.util.Date;
import java.util.HashMap;

public class verkaufsAutomat {
    private final int anzahlFaecher;
    private HashMap<Integer, ObstkuchenImp> lager;

    public int getAnzahlFaecher() {
        return anzahlFaecher;
    }
    public verkaufsAutomat(int anzahlFaecher){
        this.anzahlFaecher = anzahlFaecher;
        this.lager = new HashMap<Integer, ObstkuchenImp>();
    }
    public ObstkuchenImp create(ObstkuchenImp inputKuchen){
        int freiersFach = this.findNextFreeSlot();
        if (freiersFach == 0)
            return null;
        inputKuchen.setFachnummer(freiersFach);
        return this.lager.put(freiersFach, inputKuchen);
    }
    private int findNextFreeSlot(){
        for (int i = 1; i < this.anzahlFaecher; i++){
            if (!this.lager.containsKey(i))
                return i;
        }
        return 0;
    }
    public ObstkuchenImp delete(int fachnummer){
        return this.lager.remove(fachnummer);
    }

    public boolean update(int fachnummer){
        ObstkuchenImp kuchen = this.lager.get(fachnummer);
        if(kuchen == null){
            return false;
        }
        kuchen.setInspectionDate(new Date());
        return true;
    }
    public ObstkuchenImp read(int fachnummer){
        if(this.lager.get(fachnummer) != null){
            return this.lager.get(fachnummer);
        }else {
            return null;
        }
    }
}
