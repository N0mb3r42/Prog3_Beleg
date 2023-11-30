package Simulation1;
import verwaltungsImp.verkaufsAutomat;

import java.util.Date;

public class Simulation1 {
    private verkaufsAutomat automat;
    public Simulation1(int faecher){
        if (faecher <= 0){
            System.out.println("Kapazität darf nicht kleiner als 1 sein");
            System.exit(-1);
        }
        this.automat = new verkaufsAutomat(faecher);
    }
    public void runSim(){
        CreateKuchenThread c = new CreateKuchenThread(this.automat);
        DeleteKuchenThread d = new DeleteKuchenThread(this.automat);
        c.start();
        d.start();
    }
}
