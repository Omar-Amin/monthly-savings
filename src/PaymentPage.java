import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class PaymentPage {

    @FXML
    private Button addPayment;

    @FXML
    private ImageView homeImg;

    @FXML
    private ImageView salaryImg;



    @FXML
    private void initialize(){

    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        Controller.user = null;
        Controller.stage.hide();
        new MainUI().start(new Stage());
    }

    public void switchHome(MouseEvent mouseEvent) throws IOException {
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("fxml/MainPage.fxml")));
        main.getStylesheets().add("css/mainpage.css");
        main.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Controller.stage.setScene(main);
    }
}
