import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tables.Payment;
import tables.User;

import java.io.IOException;
import java.util.List;

public class UserSettings {

    @FXML
    private BarChart<String,Number> comparisonChart;
    @FXML
    private TextField income;
    @FXML
    private ImageView editIncome;
    @FXML
    private ImageView saveIncome;

    private Stats stats;

    @FXML
    private void initialize() {
        income.setText("$ " + Controller.user.getIncome());
        setupBarchart();
    }

    private void setupBarchart(){
        if(stats == null){
            stats = getStats();
        }
        comparisonChart.setTitle("Your income/payments compared to others");
        XYChart.Series<String,Number> user = new XYChart.Series<>();
        XYChart.Series<String,Number> avg = new XYChart.Series<>();
        avg.setName("Average");
        user.setName("You");
        user.getData().add(new XYChart.Data<>("Payments",Controller.user.getMonthlyPayment()));
        user.getData().add(new XYChart.Data<>("Income",Controller.user.getIncome()));
        avg.getData().add(new XYChart.Data<>("Payments",stats.getAveragePayments()));
        avg.getData().add(new XYChart.Data<>("Income",stats.getAverageIncome()));
        comparisonChart.getData().add(user);
        comparisonChart.getData().add(avg);
    }

    private Stats getStats(){
        Session session = MainUI.server.getSession();
        List<User> users = session.createQuery("FROM User").list();
        List<Payment> payments = session.createQuery("FROM Payment").list();

        float averagePayments = 0;
        float averageIncome = 0;

        for (User u :users) {
            averageIncome += u.getIncome();
        }
        averageIncome = averageIncome/users.size();

        for (Payment p :payments) {
            averagePayments += getMonthly(p);
        }
        averagePayments = averagePayments/users.size();
        session.close();
        return new Stats(averageIncome,averagePayments);
    }

    private int getMonthly(Payment p){
        int payments;
        if(p.getType() == tables.paymentType.daily){
            payments = 4 * 7 * p.getPrice();
        }else if(p.getType() == tables.paymentType.quarterly){
            payments = p.getPrice() / 4;
        }else if (p.getType() == tables.paymentType.yearly){
            payments = p.getPrice() / (52/4);
        } else {
            payments = p.getPrice();
        }
        return payments;
    }

    public void switchHome() throws IOException {
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("fxml/MainPage.fxml")));
        main.getStylesheets().add("css/mainpage.css");
        main.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Controller.stage.setScene(main);
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
        comparisonChart.setAnimated(false);
        comparisonChart.getData().clear();
        comparisonChart.setAnimated(true);
        setupBarchart();
        income.setEditable(false);
        saveIncome.setVisible(false);
        editIncome.setVisible(true);
    }

    public void logOut() throws IOException {
        Controller.user = null;
        Controller.stage.hide();
        new MainUI().start(new Stage());
    }

    public void closeApp() {
        Controller.stage.close();
    }

}
