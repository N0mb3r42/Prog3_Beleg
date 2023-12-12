package twoLayered;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class oldViewController {
    @FXML private TextField input;
    @FXML private Label text;
    @FXML private Label length;
    public oldViewController(){}
    @FXML private void initialize() { this.updateView(); }
    @FXML private void buttonClick(ActionEvent actionEvent) {
        this.updateView();
    }
    private void updateView(){

    }
}
