import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import tables.Payment;
import tables.paymentType;

import java.io.IOException;


public class PaymentCell extends ListCell<Payment> {

    @FXML
    private Label name;

    @FXML
    private Label cost;

    @FXML
    private Label type;

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Payment payment, boolean empty) {
        if(empty || payment == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("fxml/PaymentCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            name.setText(payment.getName());
            paymentType pt = payment.getType();
            if(pt == paymentType.daily){
                type.setText("Daily");
            }else if(pt == paymentType.monthly){
                type.setText("Monthly");
            }else if(pt == paymentType.quarterly){
                type.setText("Quarterly");
            }else{
                type.setText("Yearly");
            }
            cost.setText("$ " + payment.getPrice());
            setText(null);
            setGraphic(gridPane);
        }
    }


}
