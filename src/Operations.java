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
            } catch (IllegalArgumentException e) {
                System.out.println("Illegal argument.");
            }finally {
                session.close();
            }
        }

    }

}
