package guiAutomat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import verwaltungsImp.verkaufsAutomat;
import kuchen.Allergen;

public class ViewController {
    private verkaufsAutomat automat;
    @FXML private TextField hersteller;

    public ViewController(Integer faecher){
        this.automat = new verkaufsAutomat(faecher);
        ObservableList<Allergen> allergeneList = FXCollections.observableArrayList(kuchen.Allergen.values());
    }
    public ViewController(){
        this.automat=new verkaufsAutomat(9);
        ObservableList<Allergen> allergeneList = FXCollections.observableArrayList(kuchen.Allergen.values());
    }
    @FXML private void createButtonClick(ActionEvent actionEvent) {
        System.out.println("Create Button clicked");
        System.out.println(this.hersteller.getText());
    }
    @FXML private void readButtonClick(ActionEvent actionEvent) {
        System.out.println("Read Button clicked");
    }
    @FXML private void deleteButtonClick(ActionEvent actionEvent){
        System.out.println("Delete Button clicked");
    }

}
