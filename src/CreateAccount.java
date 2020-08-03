import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            String mail, name, password, confirm;
            mail = emailInsert.getText();
            name = fullName.getText();
            password = passwordInsert.getText();
            confirm = passwordConfirm.getText();
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
