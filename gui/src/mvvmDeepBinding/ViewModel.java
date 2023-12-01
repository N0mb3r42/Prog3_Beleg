package mvvmDeepBinding;

import businessLogic.SingletonModelWithProperties;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ViewModel {
    public SingletonModelWithProperties getModel(){return SingletonModelWithProperties.getInstance();}
    @FXML private TextField input;
    @FXML private void initialize() {
        this.input.textProperty().bindBidirectional(SingletonModelWithProperties.getInstance().textProperty());
    }
}
