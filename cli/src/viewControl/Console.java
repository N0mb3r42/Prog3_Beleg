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
    }
    private List<Allergen> parseAllergene(String[] text){
        List<Allergen> allergene = new ArrayList<>();
        if (Objects.equals(text[0], "")){
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
    private void printException(Exception e, Command.Mode mode){
        switch (mode) {
            case CREATE -> {
                System.out.println("Fehlerhafte Eingabe " + e.getMessage());
                System.out.println("Input sollte Format: [String] [String] [Decimal] [Integer] [Integer] [String, String] [String] haben");
            }
            case READ -> {
                System.out.println("Fehlerhafte Eingabe " + e.getMessage());
                System.out.println("Input sollte entweder 'all' oder [Integer] sein");
            }
            case UPDATE, DELETE -> {
                System.out.println("Fehlerhafte Eingabe " + e.getMessage());
                System.out.println("Input sollte Format: [integer] haben");
            }
        }

    }
    public void execute() {
        try (Scanner s = new Scanner(System.in)) {
            Command c = new Command();

            while (true){
                System.out.println("What do you want to do?:");
                String input = s.nextLine();
                if (input.startsWith(":")){
                    c.nextCommand(input);
                    if (input.startsWith(":e")){
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                }else{
                    String[] cakeData = input.split(" ");
                    switch (c.getMode()) {
                        case CREATE -> { //Obstkuchen Alice 4,50 386 36 Gluten,Erdnuss Apfel
                            if (cakeData.length == 1) {
                                HerstellerImp hersteller = new HerstellerImp(cakeData[0]);
                                //TODO: Hersteller Speicher?
                            } else {
                                try {
                                    HerstellerImp hersteller = new HerstellerImp(cakeData[1]);
                                    BigDecimal preis = new BigDecimal(cakeData[2].replace(",", "."));
                                    int naehrwert = Integer.parseInt(cakeData[3]);
                                    Duration haltbarkeit = Duration.ofDays(Long.parseLong(cakeData[4]));
                                    List<Allergen> allergene = null;
                                    if (!Objects.equals(cakeData[5], ",")) {
                                        allergene = this.parseAllergene(cakeData[5].split(","));
                                    }
                                    String obstsorte = cakeData[6];
                                    boolean response = this.automat.create(new ObstkuchenImp(-1, new Date(), hersteller, preis, naehrwert, haltbarkeit, allergene, obstsorte));
                                    if (response){
                                        System.out.println("Kuchen wurde eingefügt!");
                                    }else{
                                        System.out.println("Irgendwas hat nicht funktioniert (Limit oder Kuchen war fehlerhaft)");
                                    }
                                } catch (Exception e) {
                                    this.printException(e, c.getMode());
                                }
                            }
                        }
                        case READ -> {
                            try {
                                if (cakeData[0].equals("all")) {
                                    System.out.println("Maximal " + this.automat.getAnzahlFaecher() + " Fächer");
                                    for (ObstkuchenImp k : automat.read().values()) {
                                        System.out.println("Fachnummer: " + k.getFachnummer() +
                                                " | Hersteller: " + k.getHersteller().getName() +
                                                " | Preis: " + k.getPreis() +
                                                " | Nährwert: " + k.getNaehrwert() +
                                                " | Haltbarkeit: " + k.getHaltbarkeit().toString() +
                                                " | Allergen: " + k.getAllergene() +
                                                " | Obstsorte: " + k.getObstsorte());

                                    }
                                } else {
                                    Obstkuchen k = automat.read().get(Integer.parseInt(cakeData[0]));
                                    if (k != null) {
                                        System.out.println("Fachnummer: " + k.getFachnummer() +
                                                " | Hersteller: " + k.getHersteller().getName() +
                                                " | Preis: " + k.getPreis() +
                                                " | Nährwert: " + k.getNaehrwert() +
                                                " | Haltbarkeit: " + k.getHaltbarkeit().toString() +
                                                " | Allergen: " + k.getAllergene() +
                                                " | Obstsorte: " + k.getObstsorte());
                                    } else {
                                        System.out.println("Fachnummer " + cakeData[0] + " hat keinen Kuchen");
                                    }
                                }
                            } catch (Exception e) {
                                this.printException(e, c.getMode());
                            }
                        }
                        case UPDATE -> {
                            try {
                                if (this.automat.update(Integer.parseInt(cakeData[0]))) {
                                    System.out.println("Das Inspektionsdatum des Kuchen in Fachnummer: " + cakeData[0] + " wurde auf heute gesetzt.");
                                } else {
                                    System.out.println("In Fachnummer: " + cakeData[0] + " befindet sich kein Kuchen.");
                                }
                            } catch (Exception e) {
                                this.printException(e, c.getMode());
                            }
                        }
                        case DELETE -> {
                            try {
                                if (this.automat.delete(Integer.parseInt(cakeData[0]))) {
                                    System.out.println("Der Kuchen in Fachnummer: " + cakeData[0] + " wurde entfernt");
                                } else {
                                    System.out.println("In Fachnummer: " + cakeData[0] + " befindet sich kein Kuchen.");
                                }
                            } catch (Exception e) {
                                this.printException(e, c.getMode());
                            }
                        }
                        case NOMODE -> System.out.println("No Mode selected");
                    }
                }
            }
        }
    }
}
