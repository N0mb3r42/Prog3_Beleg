package minimalExample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImperativeExample extends Application {
    private Label label;
    private Button button;
    private String prefix;
    private class InternalEventHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ImperativeExample.this.label.setText(ImperativeExample.this.prefix+"\""+ImperativeExample.this.button.getText()+"\"");
        }
    };
    @Override
    public void start(Stage primaryStage) throws Exception{
        Thread.setDefaultUncaughtExceptionHandler((Thread thread,Throwable thrown)->{
            System.out.println(thread.getName()+":"+thrown.getMessage());
            thrown.printStackTrace();
        });

        Label myLabel=new Label("just a label text");
        Button myButton=new Button("click here");
        String myPrefix="clicked on ";
        //externe Klasse
        myButton.setOnAction(new ExternalEventHandler(myLabel,myButton,myPrefix));
        //interne Klasse
        this.label=myLabel;
        this.button=myButton;
        this.prefix=myPrefix;
        myButton.setOnAction(new InternalEventHandler());
        //lokale Implementierung
        EventHandler<ActionEvent> handler=new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myLabel.setText(myPrefix+"\""+myButton.getText()+"\"");
            }
        };
        myButton.setOnAction(handler);
        //anonyme Implementierung
        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myLabel.setText(myPrefix+"\""+myButton.getText()+"\"");
            }
        });
        //lambda
        myButton.setOnAction(event -> myLabel.setText(myPrefix+"\""+myButton.getText()+"\""));

        //effective final
        //myPrefix="left click on ";

        VBox vBox=new VBox();
        vBox.getChildren().add(myLabel);
        vBox.getChildren().add(myButton);
        Scene scene=new Scene(vBox);
        primaryStage.setTitle("Imperative Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
