import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.awt.*;
import java.io.IOException;
import java.util.Observable;

public class MainPage {

    @FXML
    private PieChart overview;
    @FXML
    private ImageView saveBalance;
    @FXML
    private ImageView editBalance;
    @FXML
    private TextField balance;
    @FXML
    private Label caption;

    @FXML
    private void initialize(){
        balance.setText(Controller.user.getBalance()+"");
        setupChart();
    }

    private void setupChart(){
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Unspent money",Controller.user.getIncome()-Controller.user.getMonthlyPayment()),
                new PieChart.Data("Payments",Controller.user.getMonthlyPayment())
        );
        overview.setData(data);
        overview.setLabelsVisible(false);
        overview.setLegendVisible(false);
        caption.setTextFill(Paint.valueOf("#30CA80"));
        caption.setText("Money left this month: $ " + (Controller.user.getIncome()-Controller.user.getMonthlyPayment()));

        data.get(0).getNode().addEventHandler(MouseEvent.ANY, (e) -> {
            caption.setText("Money left this month: $ " + (Controller.user.getIncome()-Controller.user.getMonthlyPayment()));
            caption.setTextFill(Paint.valueOf("#30CA80"));
        });
        data.get(1).getNode().addEventHandler(MouseEvent.ANY, (e) -> {
            caption.setText("Money spent this month: $ " + Controller.user.getMonthlyPayment());
            caption.setTextFill(Paint.valueOf("#ff4a4a"));
        });
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
        } catch (NumberFormatException ignored) {
            balance.setText(Controller.user.getBalance()+"");
        }
        balance.setEditable(false);
        saveBalance.setVisible(false);
        editBalance.setVisible(true);
    }
}
