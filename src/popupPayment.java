import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class popupPayment {

    @FXML
    private ImageView closeIcon;

    public void closeApp(MouseEvent mouseEvent) {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }
}
