public class verkaufsAutomat {
    private int anzahlFaecher;
    private ObstkuchenImp[] lager;

    public int getAnzahlFaecher() {
        return anzahlFaecher;
    }

    public verkaufsAutomat(int anzahlFaecher){
        this.anzahlFaecher = anzahlFaecher;
        this.lager = new ObstkuchenImp[anzahlFaecher];
    }
    public boolean create(ObstkuchenImp input){
        return false;
    }

    public boolean delete(int fachnummer){
        return false;
    }

    public boolean update(int fachnummer){
        return false;
    }
    public ObstkuchenImp read(int fachnummer){
        return lager[fachnummer];
    }
}
