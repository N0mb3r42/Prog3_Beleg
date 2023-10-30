import kuchen.Allergen;
import kuchen.Obstkuchen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class ObstkuchenImp implements Obstkuchen {

    private int Fachnummer;
    private Date InspectionDate;

    public ObstkuchenImp(int fachnummer, Date InspectionDate) {
        this.Fachnummer = fachnummer;
        this.InspectionDate = InspectionDate;
    }
    @Override
    public Hersteller getHersteller() {
        return null;
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return null;
    }

    @Override
    public int getNaehrwert() {
        return 0;
    }

    @Override
    public Duration getHaltbarkeit() {
        return null;
    }

    @Override
    public String getObstsorte() {
        return null;
    }

    @Override
    public BigDecimal getPreis() {
        return null;
    }

    @Override
    public Date getInspektionsdatum() {
        return this.InspectionDate;
    }
    protected void setInspektionsdatum(Date date){
        this.InspectionDate = date;
    }

    @Override
    public int getFachnummer() {
        return this.Fachnummer;
    }
}
