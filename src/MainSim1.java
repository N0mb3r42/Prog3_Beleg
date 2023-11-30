import Simulation1.Simulation1;

public class MainSim1 {
    public static void main(String[] args){
        Simulation1 sim1 = new Simulation1(Integer.parseInt(args[0]));
        sim1.runSim();
    }
}
