import tables.Payment;
import tables.User;
import server.Authentication;
import server.Server;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private Server server;
    private Scanner sc;
    private User user;

    private void run(){
        setup();
        sc = new Scanner(System.in);
        logIn();
    }

    private void logIn(){
        System.out.println("Press 1 to log in. \nPress 2 to create an account. \nPress 3 to leave the application.");
        Authentication auth = new Authentication(server);
        switch (sc.nextLine()){
            case "1":
                if((user = auth.logIn()) != null){
                    System.out.println("Logged in.");
                    menu();
                } else {
                    System.out.println("Incorrect email or password, please try again.");
                    logIn();
                }
                break;
            case "2":
                if((user = auth.createUser()) != null){
                    System.out.println("Account created.");
                    System.out.println("Logged in.");
                    menu();
                }else {
                    System.out.println("Email already taken.");
                }
                break;
            case "3":
                try {
                    server.close();
                } catch (SQLException ignored) {}
                break;
            default:
                System.out.println("Illegal argument, try again");
                logIn();
        }
    }

    private void menu(){
        System.out.println("Press 1 to change income. \nPress 2 to add a new payment. \nPress 3 to view all payments." +
                "\nPress 4 to get more information on your income and monthly payments." + " \nPress 5 to log out.");
        Operations op = new Operations(server,user,sc);
        switch (sc.nextLine()){
            case "1":
                try{
                    int income = Integer.parseInt(sc.nextLine());
                    System.out.println("Type in your monthly income:");
                    op.updateIncome(income);
                }catch (NumberFormatException e){
                    System.out.println("The income should only consist of integers, try again.");
                } finally {
                    menu();
                }
                break;
            case "2":
                op.addNewPayment();
                menu();
                break;
            case "3":
                System.out.println("Payments:");
                for (Payment p :user.getPayments()) {
                    System.out.println("Name: " + p.getName() + " Price: " + p.getPrice() + " Type: " + p.getType());
                }
                System.out.println();
                menu();
                break;
            case "4":
                op.calculateMonthly();
                menu();
                break;
            case "5":
                // TODO: Some sort of savings feature (i want to save up for a car, how much should i wait, etc.)
                System.out.println(user.getMonthlyPayment());
                menu();
                break;
            case "6":
                System.out.println("Logged out.");
                user = null;
                logIn();
                break;
            default:
                System.out.println("Illegal argument, try again");
                menu();
        }
    }

    private void admin(){
        //TODO: Admin can check overall stats, how much the average user earns, how many payments etc.
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
        server.createHibernateConfiguration();
        server.buildFactory();
    }

    /*
        //TODO: The fun part, calculating the monthly payment, basically getting stats of a user, based on his income and payments.

        // Retrieves all users.
        List users = session.createQuery("FROM Tables.User").list();
        for (Object u :users) {
            Tables.User use = (Tables.User) u;
            System.out.println(use.getId());
        }
        Tables.User users = session.get(Tables.User.class,2);

        // Makes a transaction, meaning creating an object and
        // sending it to the server.
        Transaction transaction = session.beginTransaction();
        Tables.User u1 = new Tables.User("Johnny", "Doughy","john@doe.com","1234");
        u1.setIncome(21000);
        session.save(u1);
        transaction.commit();

        // Get information based on unique attribute (it returns null if the given attribute doesn't exist)
        Criteria criteria = session.createCriteria(Tables.User.class);
        Tables.User yourObject = (Tables.User) criteria.add(Restrictions.eq("email", "john@doe.com"))
                .uniqueResult();
    */



}
