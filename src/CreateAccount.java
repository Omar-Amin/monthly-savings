import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import server.Authentication;

import java.io.IOException;

public class CreateAccount {

    private final Scene scene;
    private Button backToSign,signUp;
    private TextField emailInsert, fullName, passwordInsert, passwordConfirm;

    public CreateAccount(Scene scene){
        this.scene = scene;
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
                // TODO: Visualize that there is an error.
                System.out.println("Already exist");
            }
            auth.closeSession();
        });
        backToSign.setOnAction((e) -> {
            Controller.stage.setScene(Controller.previousScene);
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
