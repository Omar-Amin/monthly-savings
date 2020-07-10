import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AccessType;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private Configuration cfg;
    private Server server;
    private Scanner sc;



    private void run(){
        setup();
        sc = new Scanner(System.in);
        logIn();
    }

    private void logIn(){
        System.out.println("Press 1 to log in. \n Press 2 to create an account. \n Press 3 to leave the application.");
        Authentication auth = new Authentication(server);
        switch (sc.nextLine()){
            case "1":
                if(auth.logIn()){
                    System.out.println("Logged in.");
                    menu();
                } else {
                    System.out.println("Incorrect email or password, please try again.");
                    logIn();
                }
                break;
            case "2":
                if(auth.createUser()){
                    System.out.println("Account created.");
                    System.out.println("Logged in.");
                    menu();
                }else {
                    System.out.println("Email already taken.");
                }
                break;
            default:
                System.out.println("Illegal argument, try again");
                logIn();
        }
    }

    private void menu(){
        System.out.println("Press 1 to change income. \n Press 2 to add a new payment. \n Press 3 to log out.");
        switch (sc.nextLine()){
            case "1":

                break;
            case "2":
                break;
            case "3":
                System.out.println("Logged out.");
                logIn();
                break;
            default:
                System.out.println("Illegal argument, try again");
                menu();
        }
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
        server.buildFactory();
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
