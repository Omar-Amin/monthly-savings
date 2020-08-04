import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.engine.jdbc.spi.SchemaNameResolver;
import org.hibernate.tool.schema.internal.exec.ScriptSourceInputNonExistentImpl;
import server.Authentication;
import server.Server;
import tables.User;

import java.io.IOException;

public class MainUI extends Application {

    // User interface
    private Button createAccount;
    private TextField emailInsert;
    private Button logIn;
    private PasswordField passwordInsert;
    private Scene main;
    private Scene createScene;
    // Server and user
    protected static Server server;
    private User user;

    public static void main(String[] args){
        setupServer();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Controller.stage = primaryStage;
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
            Authentication authentication = new Authentication(server);
            String mail, password;
            mail = emailInsert.getText();
            password = passwordInsert.getText();
            user = authentication.logIn(mail,password);
            if(authentication.logIn(mail,password) != null){
                try {
                    primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("fxml/MainPage.fxml"))));
                } catch (IOException ignored) {}
            }else {
                // TODO: Indication of that the password was wrong
                System.out.println("WRONG!!");
            }
            authentication.closeSession();
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

}
