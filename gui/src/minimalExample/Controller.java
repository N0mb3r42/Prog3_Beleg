package minimalExample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML private URL location;
    @FXML private ResourceBundle resources;
    @FXML private void initialize() { this.myLabel.setText("just a label text"); }
    @FXML private Label myLabel;
    private String myPrefix="clicked on ";
    @FXML private void buttonClick(ActionEvent actionEvent) {
        Button myButton=(Button)actionEvent.getSource();
        this.myLabel.setText(myPrefix+"\""+myButton.getText()+"\"");
    }
}
