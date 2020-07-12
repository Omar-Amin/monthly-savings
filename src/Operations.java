import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Operations {
    private Session session;
    private User user;

    Operations(Server server, User user){
        this.session = server.getSession();
        this.user = user;
    }

    void updateIncome(int income){
        try{
            Transaction tr = session.beginTransaction();
            User u1 = session.get(User.class,user.getId());
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

}
