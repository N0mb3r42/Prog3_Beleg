package guiAutomat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kuchen.Obstkuchen;
import kuchenImp.ObstkuchenImp;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class ViewController {
    private verkaufsAutomat automat;
    @FXML private TextField hersteller;
    @FXML private TextField preis;
    @FXML private TextField haltbarkeit;
    @FXML private TextField naehrwert;
    @FXML private TextField zutaten;
    @FXML private TextField allergene;
    @FXML private TextField fachnummer;
    @FXML private ChoiceBox delete_choice;
    @FXML private ListView read_output;
    @FXML private Button create_button;

    public ViewController(Integer faecher){
        this.automat = new verkaufsAutomat(faecher);
        ObservableList<Allergen> allergeneList = FXCollections.observableArrayList(kuchen.Allergen.values());
    }
    public ViewController(){
        this.automat=new verkaufsAutomat(9);
        ObservableList<Allergen> allergeneList = FXCollections.observableArrayList(kuchen.Allergen.values());
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
    private void printException(Exception e, String operation){
        switch (operation) {
            case "Create" -> {
                Alert popup = new Alert(Alert.AlertType.INFORMATION); //Referenz: https://code.makery.ch/blog/javafx-dialogs-official/
                popup.setTitle("Fehlerhafte Eingabe ");
                popup.setHeaderText("Fehlerhafte Eingabe " + e.getMessage());
                popup.setContentText("Input sollte folgende Formate haben:\n" +
                        "Hersteller: String\n" +
                        "Preis: Kommazahl\n" +
                        "Haltbarkeit: Anzahl Tage\n" +
                        "Nährwert: in Kilokalorien \n" +
                        "Allergene: Kommagetrennte Strings ohne Leerzeichen. Frei Lassen wenn es keine gibt (Möglichkeiten: Erdnuss, Gluten, Haselnuss,Sesamsamen)\n" +
                        "Zutaten: String\n");
                popup.showAndWait();
            }
            case "Read" -> {
                System.out.println("Fehlerhafte Eingabe " + e.getMessage());
                System.out.println("Input sollte entweder 'all' oder [Integer] sein");
            }
            case "Delete" -> {
                System.out.println("Fehlerhafte Eingabe " + e.getMessage());
                System.out.println("Input sollte Format: [integer] haben");
            }
        }

    }
    @FXML private void createButtonClick(ActionEvent actionEvent) {
        System.out.println("Create Button clicked");
        System.out.println(this.hersteller.getText());
        System.out.println(this.preis.getText());
        System.out.println(this.haltbarkeit.getText());
        System.out.println(this.naehrwert.getText());
        System.out.println(this.zutaten.getText());
        System.out.println(this.allergene.getText());
        try{
            HerstellerImp hersteller = new HerstellerImp(this.hersteller.getText());
            BigDecimal preis = new BigDecimal(this.preis.getText().replace(",", "."));
            int naehrwert = Integer.parseInt(this.naehrwert.getText());
            Duration haltbarkeit = Duration.ofDays(Long.parseLong(this.haltbarkeit.getText()));
            List<Allergen> allergene = null;
            if (!Objects.equals(this.allergene.getText(), "")) {
                allergene = this.parseAllergene(this.allergene.getText().split(","));
            }
            String obstsorte = this.zutaten.getText();
            ObstkuchenImp kuchen = new ObstkuchenImp(-1, new Date(), hersteller, preis, naehrwert, haltbarkeit, allergene, obstsorte);
            boolean response = this.automat.create(kuchen);
            if (response){
                System.out.println("Kuchen wurde eingefügt!");
                System.out.println(kuchen.getFachnummer());
            }else{
                System.out.println("Irgendwas hat nicht funktioniert (Limit oder Kuchen war fehlerhaft)");
            }
        } catch (Exception e) {
            this.printException(e, "Create");
        }
        this.updateView();

    }
    @FXML private void readButtonClick(ActionEvent actionEvent) {
        System.out.println("Read Button clicked");
        Collection<ObstkuchenImp> lager = this.automat.readKuchen();
        try{
            this.read_output.getItems().clear();
            if (this.fachnummer.getText().equals("")) {
                for (ObstkuchenImp k : automat.read().values()) {
                    this.read_output.getItems().add("Fachnummer: " + k.getFachnummer() +
                            " | Hersteller: " + k.getHersteller().getName() +
                            " | Preis: " + k.getPreis() +
                            " | Nährwert: " + k.getNaehrwert() +
                            " | Haltbarkeit: " + k.getHaltbarkeit().toString() +
                            " | Allergen: " + k.getAllergene() +
                            " | Obstsorte: " + k.getObstsorte());

                }
            } else {
                Obstkuchen k = automat.read().get(Integer.parseInt(this.fachnummer.getText()));
                if (k != null) {
                    this.read_output.getItems().add("Fachnummer: " + k.getFachnummer() +
                            " | Hersteller: " + k.getHersteller().getName() +
                            " | Preis: " + k.getPreis() +
                            " | Nährwert: " + k.getNaehrwert() +
                            " | Haltbarkeit: " + k.getHaltbarkeit().toString() +
                            " | Allergen: " + k.getAllergene() +
                            " | Obstsorte: " + k.getObstsorte());
                }
            }
        } catch (Exception e) {
            this.printException(e, "Read");
        }
    }
    @FXML private void deleteButtonClick(ActionEvent actionEvent){
        try{
            System.out.println("Delete Button clicked");
            ObstkuchenImp kuchen = (ObstkuchenImp) this.delete_choice.getSelectionModel().getSelectedItem();
            this.automat.delete(kuchen.getFachnummer());

        } catch (Exception e) {
            this.printException(e, "Delete");
        }
        this.updateView();

    }
    private void updateView(){
        this.create_button.setDisable(this.automat.findNextFreeSlot() == 0);
        this.delete_choice.getItems().clear();
        for (ObstkuchenImp k : automat.read().values()) {
            this.delete_choice.getItems().add(k);
        }
    }

}
