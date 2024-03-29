package viewControl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import kuchen.Obstkuchen;
import kuchenImp.KuchenImp;
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
                    System.out.println(c.nextCommand(input));
                    if (input.startsWith(":e")){
                        System.exit(0);
                    }
                }else{
                    String[] cakeData = input.split(" ");
                    switch (c.getMode()) {
                        case CREATE -> { //Obstkuchen Alice 4,50 386 36 Gluten,Erdnuss Apfel null
                            if (cakeData.length == 1) {
                                HerstellerImp hersteller = new HerstellerImp(cakeData[0]);
                                this.automat.addHersteller(hersteller.getName());
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
                                    this.automat.addHersteller(hersteller.getName());
                                    boolean response = this.automat.create(
                                            cakeData[0],
                                            hersteller,
                                            preis,
                                            naehrwert,
                                            haltbarkeit,
                                            allergene,
                                            (cakeData[0].equals("Obstkuchen") || cakeData[0].equals("Obsttorte")) ? cakeData[6] : null,
                                            (cakeData[0].equals("Kremkuchen") || cakeData[0].equals("Obsttorte")) ? cakeData[7] : null
                                    );

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
                                    for (KuchenImp k : automat.readKuchen()) {
                                        System.out.println(k.toString());
                                    }
                                } else {
                                    KuchenImp k = automat.readKuchen(Integer.parseInt(cakeData[0]));
                                    if (k != null) {
                                        System.out.println(k.toString());
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
