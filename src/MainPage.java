import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage {

    @FXML
    private void initialize(){
        System.out.println("Main page");
    }

    public void closeApp(MouseEvent mouseEvent) {
        Controller.stage.close();
    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        Controller.stage.hide();
        new MainUI().start(new Stage());
    }
}
