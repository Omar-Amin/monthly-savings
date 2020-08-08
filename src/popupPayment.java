import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tables.Payment;
import tables.paymentType;


public class popupPayment {

    @FXML
    private TextField paymentName;

    @FXML
    private ComboBox<String> paymentType;

    @FXML
    private TextField paymentCost;

    @FXML
    private ImageView closeIcon;

    @FXML
    private void initialize(){
        ObservableList<String> options = FXCollections.observableArrayList("Daily", "Monthly", "Quarterly", "Yearly");
        paymentType.setItems(options);
    }

    public void closeApp() {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        stage.close();
    }

    /**
     * Creates payment and adds it to the user.
     * */
    public void updatePayments() throws NumberFormatException {
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        if(!paymentCost.getText().isEmpty() && !paymentName.getText().isEmpty() && paymentType.getValue() != null){
            paymentType ptype;

            switch (paymentType.getValue()) {
                case "Daily":
                    ptype = tables.paymentType.daily;
                    break;
                case "Monthly":
                    ptype = tables.paymentType.monthly;
                    break;
                case "Quarterly":
                    ptype = tables.paymentType.quarterly;
                    break;
                default:
                    ptype = tables.paymentType.yearly;
                    break;
            }

            Payment payment = new Payment(paymentName.getText(), Integer.parseInt(paymentCost.getText()),ptype);
            int monthly = getMonthly(payment);
            Controller.user.addPayment(payment);
            Controller.user.setMonthlyPayment(Controller.user.getMonthlyPayment() + monthly);
            try (Session session = MainUI.server.getSession()) {
                Transaction tr = session.beginTransaction();
                payment.setUser(Controller.user);
                session.save(payment);
                session.update(Controller.user);
                tr.commit();
            } catch (HibernateException e) {
                System.out.println("Illegal argument.");
            }
            stage.close();
        }
    }

    /**
     * Get information on how much you pay for the payment pr. month.
     * */
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
