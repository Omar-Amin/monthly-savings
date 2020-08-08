import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tables.Payment;

import java.io.IOException;

public class PaymentPage {

    @FXML
    private ListView<Payment> listPayment;

    @FXML
    private Button addPayment;

    private int current = 0;

    @FXML
    private void initialize(){
        addPayment.setOnAction((e) -> {
            try {
                Scene payment = new Scene(FXMLLoader.load(getClass().getResource("fxml/popupPayment.fxml")));
                payment.getStylesheets().add("css/popup.css");
                payment.setFill(javafx.scene.paint.Color.TRANSPARENT);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(payment);
                stage.show();

            } catch (IOException ignored) {}
        });
        ObservableList<Payment> payments = FXCollections.observableArrayList(Controller.user.getPayments());
        listPayment.setItems(payments);
        listPayment.setCellFactory(p -> new PaymentCell());
        current = payments.size();
    }

    public void logOut() throws IOException {
        Controller.user = null;
        Controller.stage.hide();
        new MainUI().start(new Stage());
    }

    public void closeApp() {
        Controller.stage.close();
    }

    public void refresh() {
        if(current != Controller.user.getPayments().size()){
            ObservableList<Payment> payments = FXCollections.observableArrayList(Controller.user.getPayments());
            listPayment.setItems(payments);
            listPayment.setCellFactory(p -> new PaymentCell());
        }
    }

    public void switchSalary() throws IOException {
        Scene salary = new Scene(FXMLLoader.load(getClass().getResource("fxml/UserSettings.fxml")));
        salary.getStylesheets().add("css/usersettings.css");
        salary.setFill(Color.TRANSPARENT);
        Controller.stage.setScene(salary);
    }

    public void switchHome() throws IOException {
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("fxml/MainPage.fxml")));
        main.getStylesheets().add("css/mainpage.css");
        main.setFill(Color.TRANSPARENT);
        Controller.stage.setScene(main);
    }
}
