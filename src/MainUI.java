import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainUI extends Application {

    public Button createAccount;
    public TextField emailInsert;
    public Button logIn;
    public PasswordField passwordInsert;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("fxml/MainUI.fxml"));
        primaryStage.setTitle("Monthly savings");
        Scene scene = new Scene(parent);
        scene.getStylesheets().add("css/login.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        emailInsert = (TextField) scene.lookup("#emailInsert");
        passwordInsert = (PasswordField) scene.lookup("#passwordInsert");
        logIn = (Button) scene.lookup("#logIn");
        createAccount = (Button) scene.lookup("#createAccount");

        logIn.setOnAction((e) ->{
            System.out.println(emailInsert.getText());
            System.out.println(passwordInsert.getText());
        });

        createAccount.setOnAction((e) -> {
            try {
                scene.setRoot(FXMLLoader.load(getClass().getResource("fxml/createAccount.fxml")));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

}
