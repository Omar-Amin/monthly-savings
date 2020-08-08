import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class UserSettings {

    @FXML
    private TextField income;

    @FXML
    private ImageView editIncome;

    @FXML
    private ImageView saveIncome;

    @FXML
    private void initialize() {
        income.setText("$ " + Controller.user.getIncome());
    }

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

    public void changeField() {
        income.setText(String.valueOf(Controller.user.getIncome()));
        income.setEditable(true);
        editIncome.setVisible(false);
        saveIncome.setVisible(true);
    }

    public void saveMethod() {
        try (Session session = MainUI.server.getSession()) {
            Transaction tr = session.beginTransaction();
            Controller.user.setIncome(Integer.parseInt(income.getText()));
            session.update(Controller.user);
            tr.commit();
        } catch (NumberFormatException ignored){}
        finally {
            income.setText("$ " + Controller.user.getIncome());
        }
        income.setEditable(false);
        saveIncome.setVisible(false);
        editIncome.setVisible(true);
    }
}
