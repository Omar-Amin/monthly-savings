import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tables.Payment;
import tables.paymentType;

import java.io.IOException;
import java.util.List;


public class PaymentCell extends ListCell<Payment> {

    @FXML
    private Label name;

    @FXML
    private Label cost;

    @FXML
    private Label type;

    @FXML
    private ImageView deletePayment;

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Payment payment, boolean empty) {
        super.updateItem(payment,empty);
        if(empty || payment == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("fxml/PaymentCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException ignored) {}
            }

            // deletes a payment from the user
            // and updates the server about the deletion
            deletePayment.setOnMouseClicked((e) -> {
                deletePayment.setOnMouseClicked(null);
                Session session = MainUI.server.getSession();
                List<Payment> payments = Controller.user.getPayments();
                for (int i = 0; i < payments.size(); i++) {
                    Payment p = payments.get(i);
                    if(p.getId() == payment.getId()){
                        Controller.user.deletePayment(i);
                        Controller.user.setMonthlyPayment(Controller.user.getMonthlyPayment()-updateMonthly(payment));
                        break;
                    }
                }

                try{
                    Transaction tr = session.beginTransaction();
                    session.delete(payment);
                    session.update(Controller.user);
                    tr.commit();
                } catch (HibernateException ignored) {
                } finally {
                    session.close();
                }
            });

            name.setText(payment.getName());
            paymentType pt = payment.getType();
            if(pt == paymentType.daily){
                type.setText("Daily");
            } else if(pt == paymentType.monthly){
                type.setText("Monthly");
            } else if(pt == paymentType.quarterly){
                type.setText("Quarterly");
            } else{
                type.setText("Yearly");
            }
            cost.setText("$ " + payment.getPrice());
            setText(null);
            setGraphic(gridPane);
        }
    }

    private int updateMonthly(Payment p){
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
