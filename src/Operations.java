import tables.Payment;
import tables.User;
import tables.paymentType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Operations {
    private final Session session;
    private final Scanner sc;
    private final User user;
    private final int id;

    Operations(Server server, User user, Scanner sc){
        this.session = server.getSession();
        this.user = user;
        this.sc = sc;
        this.id = user.getId();
    }

    /**
     * Updating the income for the user.
     * @param income: Type is integer.
     * */
    void updateIncome(int income){
        try{
            Transaction tr = session.beginTransaction();
            User u1 = session.get(User.class,id);
            u1.setIncome(income);
            user.setIncome(income);
            session.update(u1);
            tr.commit();
        } catch (HibernateException e){
            System.out.println("Something went wrong with the server.");
        }finally {
            session.close();
        }
    }

    /**
     * Adds a new payment to the database and user object.
     * */
    void addNewPayment(){
        Payment payment = null;
        try {
            System.out.println("Name of the payment:");
            String name = sc.nextLine();
            System.out.println("Do you pay the payment once, daily, monthly, quarterly or yearly? (if it is daily type 'daily' etc.)");
            paymentType type = paymentType.valueOf(sc.nextLine());
            System.out.println("Cost of the payment:");
            int cost = Integer.parseInt(sc.nextLine());
            payment = new Payment(name,cost,type);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument.");
        }

        if(payment != null){
            try{
                Transaction tr = session.beginTransaction();
                User u1 = session.get(User.class,id);
                u1.addPayment(payment);
                payment.setUser(u1);
                user.addPayment(payment);
                session.save(payment);
                session.update(u1);
                tr.commit();
            } catch (HibernateException e) {
                System.out.println("Illegal argument.");
            }finally {
                session.close();
            }
        }
    }

    /**
     * Calculates how much the user spends on the payments,
     * and how much money left he has at the end of the month based on the income.
     * */
    void calculateMonthly(){
        int payments = calculatePayments();
        user.setMonthlyPayment(payments);
        System.out.println("Monthly payments: " + payments);
        System.out.println("Money left after payments: " + (user.getIncome()-payments) + "\n");
    }

    private int calculatePayments(){
        int payments = 0;
        for (Payment p : user.getPayments()) {
            if(p.getType() == paymentType.daily){
                payments += 4 * 7 * p.getPrice();
            }else if(p.getType() == paymentType.quarterly){
                payments += p.getPrice() / 4;
            }else if (p.getType() == paymentType.yearly){
                payments += p.getPrice() / (52/4);
            } else {
                payments += p.getPrice();
            }
        }
        return payments;
    }

    /**
     * Calculates how many months it would take to save up
     * x amount of money.
     * */
    void calculateSavings(){
        System.out.println("Amount of money you want to save: ");
        try{
            int cost = Integer.parseInt(sc.nextLine());
            int moneyLeft = user.getIncome()-calculatePayments();
            if(moneyLeft <= 0){
                System.out.println("You don't earn enough money or you have too many payments to save up.");
            }else{
                System.out.println("It will take you approximately " + (int) Math.ceil((double) cost/moneyLeft) + " month(s). \n");
            }
        }catch (NumberFormatException ignore){
            System.out.println("Not an integer.");
        }
    }

    /**
     * Deletes a payment from the user and server.
     * */
    void deletePayment(){
        System.out.println("Choose a payment by its id to get deleted: ");
        List<Payment> payments = user.getPayments();
        int amountOfPayments = payments.size();
        for (int i = 0; i < amountOfPayments; i++) {
            Payment p = payments.get(i);
            System.out.println("ID: " + i + " Name: " + p.getName() + " Price: " + p.getPrice() + " Type: " + p.getType());
        }
        int index = 0;
        try{
            index = Integer.parseInt(sc.nextLine());
            if(index >= amountOfPayments || index < 0){
                System.out.println("This payment ID does not exist.");
                return;
            }
        }catch (NumberFormatException ignore){
            System.out.println("Not an integer.");
        }

        try{
            Transaction tr = session.beginTransaction();
            User u1 = session.get(User.class,id);
            session.delete(u1.getPayments().get(index));
            u1.deletePayment(index);
            user.deletePayment(index);
            session.update(u1);
            tr.commit();
        } catch (HibernateException e) {
            System.out.println("Server error.");
        }finally {
            session.close();
        }
    }


}
