package mvvm;

import businessLogic.SimpleModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ViewModel {
    private StringProperty inputProperty=new SimpleStringProperty();
    private StringProperty textProperty=new SimpleStringProperty();
    private StringProperty lengthProperty=new SimpleStringProperty();
    private SimpleModel model;

    public StringProperty inputProperty(){return this.inputProperty;}
    public void setInput(String value){ this.inputProperty.set(value); }
    public String getInput(){ return this.inputProperty.get(); }
    public StringProperty textProperty(){return this.textProperty;}
    public String getText(){ return this.textProperty.get(); }
    public StringProperty lengthProperty(){return this.lengthProperty;}
    public String getLength(){ return this.lengthProperty.get(); }

    public ViewModel(){
        this.model=new SimpleModel();
        this.updateProperties();
    }
    public void buttonClick(ActionEvent actionEvent) {
        this.model.setText(this.inputProperty.get());
        this.updateProperties();
    }
    private void updateProperties(){
        this.textProperty.set(this.model.getText());
        this.lengthProperty.set(""+this.model.getLength());
    }
    @FXML private TextField input;
    @FXML private void initialize() {
        this.input.textProperty().bindBidirectional(this.inputProperty);
        this.updateProperties();
    }
}
