package viewControl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import kuchen.Obstkuchen;
import kuchenImp.ObstkuchenImp;
import util.Command;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;
import kuchen.Allergen;

public class Console {
    private verkaufsAutomat automat;
    public Console(verkaufsAutomat automat){
        this.automat=automat;
        this.running = true;
    }
    private boolean running;
    private List<Allergen> parseAllergene(String[] text){
        List<Allergen> allergene = new ArrayList<>();
        if (text[0] == ""){
            return null;
        }
        for (String s : text) {
            for(Allergen a: Allergen.values()){
                if(a.name().equals(s)){
                    allergene.add(a);
                }
            }
        }
        return allergene;
    }
    public void execute() {
        try (Scanner s = new Scanner(System.in)) {
            Command c = new Command();

            while (this.running){
                System.out.println("What do you want to do?:");
                String input = s.nextLine();
                if (input.startsWith(":")){
                    c.nextCommand(input);
                    if (input.startsWith(":e")){
                        this.running = false;
                        System.out.println("Exiting...");
                    }
                }else{
                    String[] cakeData = input.split(" ");
                    switch (c.mode) {
                        case CREATE: //Kremkuchen Alice 4,50 386 36 Gluten,Erdnuss Butter
                            if (cakeData.length == 1){
                                HerstellerImp hersteller = new HerstellerImp(cakeData[0]);
                            }else{
                                HerstellerImp hersteller = new HerstellerImp(cakeData[1]);
                                BigDecimal preis = new BigDecimal(cakeData[2].replace(",", "."));
                                int naehrwert = Integer.parseInt(cakeData[3]);
                                Duration haltbarkeit = Duration.ofDays(Long.parseLong(cakeData[4]));
                                List<Allergen> allergene= this.parseAllergene(cakeData[5].split(","));
                                String obstsorte = cakeData[6];
                                this.automat.create(new ObstkuchenImp(-1, new Date(), hersteller, preis, naehrwert, haltbarkeit, allergene, obstsorte));
                            }
                            break;
                        case READ:
                            if (cakeData[0].equals("all")){
                                for (ObstkuchenImp k : automat.read().values()){
                                    System.out.println("Fachnummer: " + k.getFachnummer() +
                                            " | Hersteller: " + k.getHersteller().getName() +
                                            " | Preis: " + k.getPreis() +
                                            " | Nährwert: " + k.getNaehrwert() +
                                            " | Haltbarkeit: " + k.getHaltbarkeit().toString() +
                                            " | Allergen: "+ k.getAllergene() +
                                            " | Obstsorte: "+ k.getObstsorte());

                                }
                            }else{Obstkuchen k = automat.read().get(Integer.parseInt(cakeData[0]));
                               if (k != null){
                                   System.out.println("Fachnummer: " + k.getFachnummer() +
                                           " | Hersteller: " + k.getHersteller().getName() +
                                           " | Preis: " + k.getPreis() +
                                           " | Nährwert: " + k.getNaehrwert() +
                                           " | Haltbarkeit: " + k.getHaltbarkeit().toString() +
                                           " | Allergen: "+ k.getAllergene() +
                                           " | Obstsorte: "+ k.getObstsorte());
                               }else{
                                   System.out.println("Fachnummer " + cakeData[0] + " hat keinen Kuchen");
                               }

                            }
                            break;
                        case UPDATE:
                            if (this.automat.update(Integer.parseInt(cakeData[0]))){
                                System.out.println("Das Inspektionsadtum des Kuchen in Fachnummer: "+ cakeData[0] + " wurde auf heute gesetzt.");
                            }else{
                                System.out.println("In Fachnummer: "+ cakeData[0] + " befindet sich kein Kuchen.");
                            }
                            break;
                        case DELETE:
                            if (this.automat.delete(Integer.parseInt(cakeData[0]))){
                                System.out.println("Der Kuchen in Fachnummer: "+ cakeData[0] + " wurde entfernt");
                            }else{
                                System.out.println("In Fachnummer: "+ cakeData[0] + " befindet sich kein Kuchen.");
                            }
                            break;
                        case NOMODE:
                            System.out.println("No Mode selected");
                            break;
                    }
                }
            }
        }
    }
}
