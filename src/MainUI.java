import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainUI extends Application {

    private Button createAccount;
    private TextField emailInsert;
    private Button logIn;
    private PasswordField passwordInsert;
    private Scene main;
    private Scene createScene;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        setController();
        primaryStage.setTitle("Monthly savings");
        primaryStage.setScene(main);
        primaryStage.show();
        new CreateAccount(createScene,main,primaryStage);
        emailInsert = (TextField) main.lookup("#emailInsert");
        passwordInsert = (PasswordField) main.lookup("#passwordInsert");
        logIn = (Button) main.lookup("#logIn");
        createAccount = (Button) main.lookup("#createAccount");

        logIn.setOnAction((e) ->{
            System.out.println(emailInsert.getText());
            System.out.println(passwordInsert.getText());
        });

        createAccount.setOnAction((e) -> {
            primaryStage.setScene(createScene);
        });
    }

    private void setController() throws IOException {
        Parent firstPage = FXMLLoader.load(getClass().getResource("fxml/MainUI.fxml"));
        main = new Scene(firstPage);
        main.getStylesheets().add("css/login.css");
        createScene = new Scene(FXMLLoader.load(getClass().getResource("fxml/CreateAccount.fxml")));
        createScene.getStylesheets().add("css/login.css");

    }

}
