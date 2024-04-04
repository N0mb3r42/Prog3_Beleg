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
import io.Serializer;

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
                System.out.println("Input sollte Format: [String] [String] [Decimal] [Integer] [Integer] [String, String] [[String] [String]] haben");
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
    private String performPersitance(String userInput){
        switch (userInput) {
            case "saveJOS" -> {
                Serializer.serializer("JOSvk.txt", this.automat);
                return "Saved with JOS";
            }
            case "loadJOS" -> {
                this.automat = (verkaufsAutomat) Serializer.deserialize("JOSvk.txt");
                return "Loaded with JOS";
            }
            default -> {
                return "no fitting Input [saveJOS, loadJOS, saveJBP, loadJBP]";
            }
        }
    }
    public void execute() {
        try (Scanner s = new Scanner(System.in)) {
            Command c = new Command();

            while (true){
                System.out.println("Current Mode: " + c.getMode());
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
                        case CREATE -> { //Obstkuchen Alice 4,50 386 36 Gluten,Erdnuss Apfel
                            if (cakeData.length == 1) {
                                HerstellerImp hersteller = new HerstellerImp(cakeData[0]);
                                boolean response = this.automat.addHersteller(hersteller.getName());
                                if (response){
                                    System.out.println("Hersteller: " +  hersteller.getName() + " added!");
                                }else{
                                    System.out.println("Hersteller: " +  hersteller.getName() + " could not be added!");
                                }
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
                                    //this.automat.addHersteller(hersteller.getName());
                                    boolean response = this.automat.create(
                                            cakeData[0],
                                            hersteller,
                                            preis,
                                            naehrwert,
                                            haltbarkeit,
                                            allergene,
                                            (cakeData[0].equals("Obstkuchen") || cakeData[0].equals("Obsttorte") || cakeData[0].equals("Kremkuchen")) ? cakeData[6] : null,
                                            (cakeData[0].equals("Obsttorte")) ? cakeData[7] : null
                                    );

                                    if (response){
                                        System.out.println("Kuchen wurde eingefügt!");
                                    }else{
                                        System.out.println("Irgendwas hat nicht funktioniert (Limit erreicht, Hersteller wurde nicht hinzugefügt oder Kuchen war fehlerhaft)");
                                    }
                                } catch (Exception e) {
                                    this.printException(e, c.getMode());
                                }
                            }
                        }
                        case READ -> {
                            try {
                                if (cakeData[0].contains("hersteller")) {
                                    System.out.println("Anzeige aller Hersteller mit Anzahl ihrer Kuchen:");
                                    for(Map.Entry<String, Integer> entry : automat.getHerstellerMitKuchenAnzahl().entrySet()) {
                                        String hersteller = entry.getKey();
                                        Integer anzahl = entry.getValue();
                                        System.out.println(hersteller + ": " + anzahl.toString());
                                    }
                                } else if (cakeData[0].contains("kuchen")){
                                    Collection<KuchenImp> kuchenListe = new ArrayList<KuchenImp>();
                                    if (cakeData.length > 1){
                                        for (int i = 1; i < cakeData.length; i++ )
                                            kuchenListe.addAll(this.automat.readKuchen(cakeData[i]));
                                    }else{
                                        kuchenListe.addAll(this.automat.readKuchen());
                                    }
                                    if (kuchenListe.size() != 0){
                                        for (KuchenImp k : kuchenListe){
                                            if (k != null) {
                                                System.out.println(k.toString());
                                            }
                                        }
                                    }
                                } else if (cakeData[0].contains("allergene")) {
                                    ArrayList<Allergen> allergeneList = new ArrayList<>();
                                    for (KuchenImp k: this.automat.readKuchen()){
                                        for (Allergen a: k.getAllergene()){
                                            if (!allergeneList.contains(a)){
                                                allergeneList.add(a);
                                            }
                                        }
                                    }
                                    if (cakeData[1].equals("i")){
                                        for (Allergen a: allergeneList){
                                            System.out.println(a);
                                        }
                                    }else if (cakeData[1].equals("e")){
                                        List<Allergen> allAllergeneList = List.of(Allergen.values());
                                        for (Allergen a: allAllergeneList){
                                            if(!allergeneList.contains(a)){
                                                System.out.println(a);
                                            }
                                        }
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
                                    System.out.println("Der Kuchen in Fachnummer: " + cakeData[0] + " wurde entfernt.");
                                } else {
                                    System.out.println("In Fachnummer: " + cakeData[0] + " befindet sich kein Kuchen.");
                                }
                            } catch (NumberFormatException e) {
                                if (this.automat.deleteHersteller(cakeData[0])){
                                    System.out.println("Der Hersteller: " + cakeData[0] + " wurde gelöscht.");
                                }else{
                                    System.out.println("Der Hersteller: " + cakeData[0] + " existiert nicht.");
                                }
                            } catch (Exception e) {
                                this.printException(e, c.getMode());
                            }
                        }
                        case PERSIST -> {
                            System.out.println(this.performPersitance(cakeData[0]));
                        }
                        case NOMODE -> System.out.println("No Mode selected");
                    }
                }
            }
        }
    }
}
