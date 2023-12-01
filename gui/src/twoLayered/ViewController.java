package twoLayered;

import businessLogic.SimpleModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ViewController {
    @FXML private TextField input;
    @FXML private Label text;
    @FXML private Label length;
    private SimpleModel model;
    public ViewController(){this.model=new SimpleModel();}
    @FXML private void initialize() { this.updateView(); }
    @FXML private void buttonClick(ActionEvent actionEvent) {
        this.model.setText(this.input.getText());
        this.updateView();
    }
    private void updateView(){
        this.text.setText(this.model.getText());
        this.length.setText(""+this.model.getLength());
    }
}
