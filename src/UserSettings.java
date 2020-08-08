import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class UserSettings {

    public void switchHome() throws IOException {
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("fxml/MainPage.fxml")));
        main.getStylesheets().add("css/mainpage.css");
        main.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Controller.stage.setScene(main);
    }

    public void logOut() throws IOException {
        Controller.user = null;
        Controller.stage.hide();
        new MainUI().start(new Stage());
    }

    public void closeApp() {
        Controller.stage.close();
    }

    public void switchPayment() throws IOException {
        Scene payment = new Scene(FXMLLoader.load(getClass().getResource("fxml/PaymentPage.fxml")));
        payment.getStylesheets().add("css/payment.css");
        payment.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Controller.stage.setScene(payment);
    }
}
