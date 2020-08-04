import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.awt.*;
import java.io.IOException;

public class MainPage {

    @FXML
    private ImageView saveBalance;
    @FXML
    private ImageView editBalance;
    @FXML
    private TextField balance;

    @FXML
    private void initialize(){
        balance.setText(Controller.user.getBalance()+"");
    }

    public void closeApp(MouseEvent mouseEvent) {
        Controller.stage.close();
    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        Controller.user = null;
        Controller.stage.hide();
        new MainUI().start(new Stage());
    }

    public void changeField(MouseEvent mouseEvent) {
        balance.setEditable(true);
        editBalance.setVisible(false);
        saveBalance.setVisible(true);
    }

    public void saveMethod(MouseEvent mouseEvent){
        try (Session session = MainUI.server.getSession()) {
            Transaction tr = session.beginTransaction();
            Controller.user.setBalance(Integer.parseInt(balance.getText()));
            session.update(Controller.user);
            tr.commit();
        } catch (NumberFormatException ignored) {}
        balance.setEditable(false);
        saveBalance.setVisible(false);
        editBalance.setVisible(true);
    }
}
