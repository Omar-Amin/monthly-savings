import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.Authentication;
import tables.User;

import java.io.IOException;

public class CreateAccount {

    private final Scene scene, prevScene;
    private final Stage primaryStage;
    private Button backToSign,signUp;
    private TextField emailInsert, fullName, passwordInsert, passwordConfirm;


    public CreateAccount(Scene scene,Scene prevScene, Stage primaryStage){
        this.scene = scene;
        this.primaryStage = primaryStage;
        this.prevScene = prevScene;
        start();
        setupActionHandlers();
    }

    private void setupActionHandlers(){
        signUp.setOnAction((e) -> {
            Authentication auth = new Authentication(MainUI.server);
            String mail, name, password, confirm;
            mail = emailInsert.getText();
            name = fullName.getText();
            password = passwordInsert.getText();
            confirm = passwordConfirm.getText();
            User newUser = auth.createUser(mail,name,password);
            if(newUser != null && password.equals(confirm)){
                try {
                    Controller.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("fxml/MainPage.fxml"))));
                } catch (IOException ignored) {}
            }else {
                // TODO: Visualize that there is an error.
                System.out.println("Already exist");
            }
            auth.closeSession();
        });
        backToSign.setOnAction((e) -> {
            primaryStage.setScene(prevScene);
        });
    }

    private void start(){
        // TextField setup
        emailInsert = (TextField) scene.lookup("#emailInsert");
        fullName = (TextField) scene.lookup("#fullName");
        passwordInsert = (PasswordField) scene.lookup("#passwordInsert");
        passwordConfirm = (PasswordField) scene.lookup("#passwordConfirm");
        // Button setup
        signUp = (Button) scene.lookup("#signUp");
        backToSign = (Button) scene.lookup("#backToSign");
    }

}
