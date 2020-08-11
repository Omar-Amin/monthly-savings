import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import server.Authentication;
import server.Server;

import java.io.IOException;

public class MainUI extends Application {

    private Label incorrect;
    private TextField emailInsert;
    private PasswordField passwordInsert;
    private Scene main;
    private Scene createScene;
    // Server and user
    protected static Server server;

    public static void main(String[] args){
        setupServer();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Controller.stage = primaryStage;
        setController();
        // Setting up stage
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(main);
        primaryStage.show();
        new CreateAccount(createScene);
        emailInsert = (TextField) main.lookup("#emailInsert");
        passwordInsert = (PasswordField) main.lookup("#passwordInsert");
        incorrect = (Label) main.lookup("#incorrect");
        Button logIn = (Button) main.lookup("#logIn");
        // User interface
        Button createAccount = (Button) main.lookup("#createAccount");

        // log in
        logIn.setOnAction((e) ->{
            Authentication authentication = new Authentication(server);
            String mail, password;
            mail = emailInsert.getText();
            password = passwordInsert.getText();
            Controller.user = authentication.logIn(mail,password);
            if(authentication.logIn(mail,password) != null){
                try {
                    Scene mainPage = new Scene(FXMLLoader.load(getClass().getResource("fxml/MainPage.fxml")));
                    mainPage.setFill(Color.TRANSPARENT);
                    mainPage.getStylesheets().add("css/mainpage.css");
                    primaryStage.setScene(mainPage);
                } catch (IOException ignored) {}
            }else {
                TranslateTransition down = new TranslateTransition();
                down.setDuration(Duration.seconds(0.45));
                down.setToY(65);
                down.setNode(incorrect);
                down.play();
                down.setOnFinished(event -> {
                    TranslateTransition up = new TranslateTransition();
                    up.setDelay(Duration.seconds(1));
                    up.setDuration(Duration.seconds(0.45));
                    up.setToY(0);
                    up.setNode(incorrect);
                    up.play();
                });
            }
            authentication.closeSession();
        });

        // creating account, switches scene
        createAccount.setOnAction((e) -> primaryStage.setScene(createScene));
    }

    // setup scenes
    private void setController() throws IOException {
        Parent firstPage = FXMLLoader.load(getClass().getResource("fxml/MainUI.fxml"));
        main = new Scene(firstPage);
        main.getStylesheets().add("css/login.css");
        createScene = new Scene(FXMLLoader.load(getClass().getResource("fxml/CreateAccount.fxml")));
        createScene.getStylesheets().add("css/login.css");
        main.setFill(Color.TRANSPARENT);
        createScene.setFill(Color.TRANSPARENT);
        Controller.previousScene = main;
    }

    private static void setupServer(){
        /*
        String connectionUrl = "jdbc:sqlserver://localhost:host";
        String user = "sa";
        String password = "password";
        String databaseName = "databasse";
        */



        server = new Server(connectionUrl,user,password,databaseName);
        server.setupServer();
        server.createHibernateConfiguration();
        server.buildFactory();
    }

    public void closeApp() {
        Controller.stage.close();
    }
}
