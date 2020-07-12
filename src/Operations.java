import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Scanner;

public class Operations {
    private Session session;
    private Scanner sc;
    private User user;
    private int id;

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

}
