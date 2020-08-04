import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage {

    @FXML
    private Text balance;

    @FXML
    private void initialize(){
        System.out.println("Main page");
        balance.setText("$ " + Controller.user.getBalance());
    }

    public void closeApp(MouseEvent mouseEvent) {
        Controller.stage.close();
    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        Controller.user = null;
        Controller.stage.hide();
        new MainUI().start(new Stage());
    }
}
