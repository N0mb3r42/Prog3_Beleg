package minimalExample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ExternalEventHandler implements EventHandler<ActionEvent> {
    private Label label;
    private Button button;
    private String prefix;
    public ExternalEventHandler(Label label,Button button,String prefix){
        this.label=label;
        this.button=button;
        this.prefix=prefix;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        this.label.setText(this.prefix+"\""+this.button.getText()+"\"");
    }
}
