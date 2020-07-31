import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainUI extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/MainUI.fxml"));
        primaryStage.setTitle("Test");
        Scene scene = new Scene(parent);
        scene.getStylesheets().add("css/login.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
