import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import guiAutomat.ViewController;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.List;

public class mainGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        List<String> args = this.getParameters().getRaw();
        ViewController view = new ViewController(Integer.parseInt(args.get(0)));
        //System.out.println(view.getClass());
        URL path = view.getClass().getResource("GUI.fxml");
        System.out.println(path);
        Parent root = FXMLLoader.load(path);
        primaryStage.setTitle("GUI");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
    public static void main(String[] args) {launch(args);}
}
