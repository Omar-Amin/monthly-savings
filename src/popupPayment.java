import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tables.Payment;
import tables.User;
import tables.paymentType;

import java.io.IOException;

public class popupPayment {

    @FXML
    private TextField paymentName;

    @FXML
    private ComboBox<String> paymentType;

    @FXML
    private TextField paymentCost;

    @FXML
    private Button savePayment;

    @FXML
    private ImageView closeIcon;

    @FXML
    private void initialize(){
        ObservableList<String> options = FXCollections.observableArrayList("Daily", "Monthly", "Quarterly", "Yearly");
        paymentType.setItems(options);
    }

    public void closeApp(MouseEvent mouseEvent) {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

    public void updatePayments(MouseEvent mouseEvent) throws NumberFormatException, IOException {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        if(!paymentCost.getText().isEmpty() && !paymentName.getText().isEmpty() && paymentType.getValue() != null){
            paymentType ptype;
            if(paymentType.getValue().equals("Daily")){
                ptype = tables.paymentType.daily;
            }else if(paymentType.getValue().equals("Monthly")){
                ptype = tables.paymentType.monthly;
            }else if(paymentType.getValue().equals("Quarterly")){
                ptype = tables.paymentType.quarterly;
            }else {
                ptype = tables.paymentType.yearly;
            }
            Payment payment = new Payment(paymentName.getText(), Integer.parseInt(paymentCost.getText()),ptype);
            int monthly = getMonthly(payment);
            Controller.user.addPayment(payment);
            Controller.user.setMonthlyPayment(Controller.user.getMonthlyPayment() + monthly);
            Session session = MainUI.server.getSession();
            try{
                Transaction tr = session.beginTransaction();
                payment.setUser(Controller.user);
                session.save(payment);
                session.update(Controller.user);
                tr.commit();
            } catch (HibernateException e) {
                System.out.println("Illegal argument.");
            }finally {
                session.close();
            }
            stage.close();
        }
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
}
