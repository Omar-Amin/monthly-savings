import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import server.Authentication;

import java.io.IOException;

public class CreateAccount {

    private Label incorrect;
    private final Scene scene;
    private Button backToSign,signUp;
    private TextField emailInsert, fullName, passwordInsert, passwordConfirm;

    public CreateAccount(Scene scene){
        this.scene = scene;
        initialize();
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
            Controller.user = auth.createUser(mail,name,password);
            if(Controller.user != null && password.equals(confirm)){
                try {
                    Scene mainPage = new Scene(FXMLLoader.load(getClass().getResource("fxml/MainPage.fxml")));
                    mainPage.setFill(Color.TRANSPARENT);
                    mainPage.getStylesheets().add("css/mainpage.css");
                    System.out.println(mainPage.getRoot());
                    Controller.stage.setScene(mainPage);
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
            auth.closeSession();
        });
        backToSign.setOnAction((e) -> Controller.stage.setScene(Controller.previousScene));
    }

    private void initialize(){
        // TextField setup
        emailInsert = (TextField) scene.lookup("#emailInsert");
        fullName = (TextField) scene.lookup("#fullName");
        passwordInsert = (PasswordField) scene.lookup("#passwordInsert");
        passwordConfirm = (PasswordField) scene.lookup("#passwordConfirm");
        // Button setup
        signUp = (Button) scene.lookup("#signUp");
        backToSign = (Button) scene.lookup("#backToSign");
        incorrect = (Label) scene.lookup("#incorrect");

    }

}
