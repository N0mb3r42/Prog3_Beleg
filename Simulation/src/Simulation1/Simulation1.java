package Simulation1;
import verwaltungsImp.verkaufsAutomat;

import java.util.Date;

public class Simulation1 {
    private verkaufsAutomat automat;
    public Simulation1(int faecher){
        this.automat = new verkaufsAutomat(faecher);
    }
    public void runSim(){
        CreateKuchenThread c = new CreateKuchenThread(this.automat);
        DeleteKuchenThread d = new DeleteKuchenThread(this.automat);
        c.start();
        d.start();
    }
}
