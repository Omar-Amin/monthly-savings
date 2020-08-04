import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainPage {

    @FXML
    private void initialize(){
        System.out.println("Main page");
    }

    public void closeApp(MouseEvent mouseEvent) {
        Controller.stage.close();
    }
}
