package verwaltungsImp;

import kuchen.Kuchen;
import kuchenImp.KremkuchenImp;
import kuchenImp.KuchenImp;
import kuchenImp.ObstkuchenImp;
import kuchenImp.ObsttorteImp;
import verwaltungsImp.HerstellerImp;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.time.Duration;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class verkaufsAutomat implements Serializable {
    static final long serializableUID = 1L;
    private final int anzahlFaecher;
    private ArrayList<HerstellerImp> herstellerListe;
    private HashMap<Integer, KuchenImp> lager;
    public int getAnzahlFaecher() {
        return anzahlFaecher;
    }

    public boolean addHersteller(String nameHersteller){
        if (this.checkExistingHersteller(nameHersteller)){
            return false;
        }
        return this.herstellerListe.add(new HerstellerImp(nameHersteller));
    }

    private boolean checkExistingHersteller(String nameHersteller){
        for (HerstellerImp hersteller : this.herstellerListe){
            if (Objects.equals(nameHersteller, hersteller.getName())){
                return true;
            }
        }
        return false;
    }

    public boolean deleteHersteller(String nameHersteller){
        HerstellerImp toBeDeleted = null;
        for (HerstellerImp hersteller : this.herstellerListe){
            if (Objects.equals(hersteller.getName(), nameHersteller)){
                toBeDeleted = hersteller;
            }
        }
        return this.herstellerListe.remove(toBeDeleted);
    }

    public verkaufsAutomat(int anzahlFaecher){
        this.anzahlFaecher = anzahlFaecher;
        this.lager = new HashMap<Integer, KuchenImp>();
        this.herstellerListe = new ArrayList<HerstellerImp>();
    }
    public Boolean create(String kuchenTyp, HerstellerImp hersteller, BigDecimal preis, int naehrwert, Duration haltbarkeit, List<Allergen> alList, String obstsorte, String kremesorte) {
        //TODO: Add Generic Kuchen
        if (Objects.equals(kuchenTyp, "Obstkuchen")){
            return this.create(new ObstkuchenImp(
                    -1,
                    new Date(),
                    hersteller,
                    preis,
                    naehrwert,
                    haltbarkeit,
                    alList,
                    obstsorte)

            );
        } else if (Objects.equals(kuchenTyp, "Kremkuchen")) {
            return this.create(new KremkuchenImp(
                    -1,
                    new Date(),
                    hersteller,
                    preis,
                    naehrwert,
                    haltbarkeit,
                    alList,
                    kremesorte)
            );
        } else if (Objects.equals(kuchenTyp, "Obsttorte")) {
            return this.create(new ObsttorteImp(
                    -1,
                    new Date(),
                    hersteller,
                    preis,
                    naehrwert,
                    haltbarkeit,
                    alList,
                    kremesorte,
                    obstsorte)
            );
        }
        return false;

    }

    private Boolean create(KuchenImp inputKuchen){
        if (inputKuchen == null) {
            return false;
        }
        int freiersFach = this.findNextFreeSlot();
        if (freiersFach == 0){
            return false;
        }
        if (!this.checkExistingHersteller(inputKuchen.getHersteller().getName())){
            return false;
        }
        inputKuchen.setFachnummer(freiersFach);
        this.lager.put(freiersFach, inputKuchen);
        return true;
    }
    public int findNextFreeSlot(){
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
        KuchenImp kuchen = this.lager.get(fachnummer);
        if(kuchen == null){
            return false;
        }
        kuchen.setInspectionDate(new Date());
        return true;
    }

    public Collection<KuchenImp> readKuchen(){
        return this.lager.values();
    }

    public KuchenImp readKuchen(int fachnummer) {
        return this.lager.get(fachnummer);
    }
}
