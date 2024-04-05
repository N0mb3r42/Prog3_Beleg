package guiAutomat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kuchen.Kuchen;
import kuchen.Obstkuchen;
import kuchenImp.KuchenImp;
import kuchenImp.ObstkuchenImp;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.sql.Array;
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
    @FXML private ListView<KuchenImp> read_output;
    @FXML private ListView<String> herstellerListView;
    @FXML private Button create_button;
    @FXML private Button update_button;
    @FXML private TextField kuchenTyp;
    @FXML private ChoiceBox sortChoice = new ChoiceBox();



    public ViewController(Integer faecher){
        this.automat = new verkaufsAutomat(faecher);
        ObservableList<Allergen> allergeneList = FXCollections.observableArrayList(kuchen.Allergen.values());
    }
    public ViewController(){
        this.automat=new verkaufsAutomat(20);
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
        Alert popup = new Alert(Alert.AlertType.INFORMATION); //Referenz: https://code.makery.ch/blog/javafx-dialogs-official/
        switch (operation) {
            case "Create" -> {
                popup.setTitle("Fehlerhafte Eingabe ");
                popup.setHeaderText("Fehlerhafte Eingabe " + e.getMessage());
                popup.setContentText("Input sollte folgende Formate haben:\n" +
                        "Hersteller: String\n" +
                        "Preis: Float\n" +
                        "Haltbarkeit: Integer\n" +
                        "Nährwert: Integer\n" +
                        "Allergene: (Möglichkeiten: Erdnuss,Gluten,Haselnuss,Sesamsamen)\n" +
                        "Zutaten: String\n");
                popup.show();
            }
            case "Read", "Update" -> {
                popup.setTitle("Fehlerhafte Eingabe ");
                popup.setHeaderText("Fehlerhafte Eingabe " + e.getMessage());
                popup.setContentText("Input sollte entweder 'all' oder eine Fachnummer sein");
                popup.show();
            }
            case "Delete" -> {

                popup.setTitle("Fehlerhafte Eingabe ");
                popup.setHeaderText("Fehlerhafte Eingabe " + e.getMessage());
                popup.setContentText("Bitte wähle einen Kuchen zum Löschen aus");
                popup.show();
            }
        }

    }
    @FXML private void createButtonClick(ActionEvent actionEvent) {
        System.out.println("Create Button clicked");
        System.out.println(this.kuchenTyp.getText());
        System.out.println(this.hersteller.getText());
        System.out.println(this.preis.getText());
        System.out.println(this.haltbarkeit.getText());
        System.out.println(this.naehrwert.getText());
        System.out.println(this.zutaten.getText());
        System.out.println(this.allergene.getText());
        String createMode = "NOMODE";
        if (
                !Objects.equals(this.kuchenTyp.getText(), "") &&
                !Objects.equals(this.hersteller.getText(), "") &&
                !Objects.equals(this.preis.getText(), "") &&
                !Objects.equals(this.haltbarkeit.getText(), "") &&
                !Objects.equals(this.naehrwert.getText(), "") &&
                !Objects.equals(this.zutaten.getText(), "")
        ){
           createMode = "CAKEMODE";
        }else if (!Objects.equals(this.hersteller.getText(), "")){
            createMode = "HERSTELLERMODE";
        }
        try{
            if (createMode.equals("CAKEMODE")) {
                HerstellerImp hersteller = new HerstellerImp(this.hersteller.getText());
                BigDecimal preis = new BigDecimal(this.preis.getText().replace(",", "."));
                int naehrwert = Integer.parseInt(this.naehrwert.getText());
                Duration haltbarkeit = Duration.ofDays(Long.parseLong(this.haltbarkeit.getText()));
                List<Allergen> allergene = null;
                if (!Objects.equals(this.allergene.getText(), "")) {
                    allergene = this.parseAllergene(this.allergene.getText().split(","));
                }
                String zutatenString = this.zutaten.getText();
                String kuchenTyp = this.kuchenTyp.getText();
                boolean response = this.automat.create(
                        kuchenTyp,
                        hersteller,
                        preis,
                        naehrwert,
                        haltbarkeit,
                        allergene,
                        (kuchenTyp.equals("Obstkuchen") || kuchenTyp.equals("Obsttorte")) ? zutatenString : null,
                        (kuchenTyp.equals("Kremkuchen") || kuchenTyp.equals("Obsttorte")) ? zutatenString : null
                );

                if (response) {
                    System.out.println("Kuchen wurde eingefügt!");
                } else {
                    System.out.println("Irgendwas hat nicht funktioniert (Limit oder Kuchen war fehlerhaft)");
                }
            }else if (createMode.equals("HERSTELLERMODE")){
                HerstellerImp hersteller = new HerstellerImp(this.hersteller.getText());
                this.automat.addHersteller(hersteller.getName());
            }else {
                throw new Exception("Es sind nicht alle benötigten Felder ausgefüllt (Für Hersteller nur Hersteller angeben)");
            }
        } catch (Exception e) {
            this.printException(e, "Create");
        }
        this.updateView();

    }
    @FXML private void readButtonClick(ActionEvent actionEvent) {
        System.out.println("Read Button clicked");
        Collection<KuchenImp> lager = this.automat.readKuchen();
        try{
            this.read_output.getItems().clear();
            if (this.fachnummer.getText().equals("")) {
                for (KuchenImp k : automat.readKuchen()) {
                    this.read_output.getItems().add(k);
                }
            } else {
                KuchenImp fk = this.automat.readKuchen(Integer.parseInt(this.fachnummer.getText()));
                if (fk != null) {
                    this.read_output.getItems().add(fk);
                }else{
                    this.read_output.getItems().add(null);
                }
            }
        } catch (Exception e) {
            this.printException(e, "Read");
        }
    }
    @FXML private void deleteButtonClick(ActionEvent actionEvent){
        try{
            System.out.println("Delete Button clicked");
            KuchenImp k = this.read_output.getSelectionModel().getSelectedItem();
            this.automat.delete(k.getFachnummer());
        } catch (Exception e) {
            this.printException(e, "Delete");
        }
        this.updateView();

    }
    @FXML private void updateButtonClick(ActionEvent actionEvent){
        try{
            System.out.println("Update Button clicked");
            KuchenImp k = this.read_output.getSelectionModel().getSelectedItem();
            this.automat.update(k.getFachnummer());
        } catch (Exception e) {
            this.printException(e, "Read");
        }
        this.updateView();

    }
    public void updateView(){
        this.create_button.setDisable(this.automat.findNextFreeSlot() == 0);
        this.read_output.getItems().clear();
        this.herstellerListView.getItems().clear();
        this.sortChoice.getItems().clear();
        this.sortChoice.getItems().addAll("Fachnummer", "Hersteller", "verbleibende Haltbarkeit", "Inspektionsdatum");
        for (int i = 1; i <= this.automat.getAnzahlFaecher(); i++) {
            KuchenImp k = this.automat.readKuchen(i);
            if (k != null){
                this.read_output.getItems().add(k);
            }else{
                this.read_output.getItems().add(null);
            }

        }
        for (String h: this.automat.getHerstellerMitKuchenAnzahl().keySet()) {
                this.herstellerListView.getItems().add(h);
        }


    }

}
