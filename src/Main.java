import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private Configuration cfg;
    private Server server;

    private void run(){

        logIn();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();


        sessionFactory.close();
        session.close();
    }

    private void logIn(){

    }


    private void setup(){
        /*
        String connectionUrl = "jdbc:sqlserver://localhost:host";
        String user = "sa";
        String password = "password";
        String databaseName = "databasse";
        */

        server = new Server(connectionUrl,user,password,databaseName);
        server.setupServer();

        cfg = server.createHibernateConfiguration();
    }

    /*
        //TODO: Users that can create account, log in etc.

        // Retrieves all users.
        List users = session.createQuery("FROM User").list();
        for (Object u :users) {
            User use = (User) u;
            System.out.println(use.getId());
        }
        User users = session.get(User.class,2);

        // Makes a transaction, meaning creating an object and
        // sending it to the server.
        Transaction transaction = session.beginTransaction();
        User u1 = new User("Johnny", "Doughy","john@doe.com","1234");
        u1.setIncome(21000);
        session.save(u1);
        transaction.commit();

        // Get information based on unique attribute (it returns null if the given attribute doesn't exist)
        Criteria criteria = session.createCriteria(User.class);
        User yourObject = (User) criteria.add(Restrictions.eq("email", "john@doe.com"))
                .uniqueResult();
    */



}
