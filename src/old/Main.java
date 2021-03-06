package old;

import tables.Payment;
import tables.User;
import server.Authentication;
import server.Server;

import java.sql.SQLException;
import java.util.Scanner;

// NOTE: The command line version doesn't work
// it needs a Scanner object in server/Authentication.java and old.Operations.java.
public class Main {

/*    public static void main(String[] args) {
        old.Main main = new old.Main();
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
        System.out.println("Press 1 to change income. \nPress 2 to add a new payment. \nPress 3 to view all payments.\n" +
                "Press 4 to get more information on your income and monthly payments.\n" + "Press 5 to calculate how long it takes to save up x amount of money.\n" +
                "Press 6 to delete a payment.\n"+ "Press 7 to get stats.\n" + "Press 8 to log out.");
        old.Operations op = new old.Operations(server,user,sc);
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
                op.calculateSavings();
                menu();
                break;
            case "6":
                op.deletePayment();
                menu();
                break;
            case "7":
                System.out.println("Calculating stats...");
                op.showStats();
                menu();
                break;
            case "8":
                System.out.println("Logged out.");
                user = null;
                logIn();
                break;
            default:
                System.out.println("Illegal argument, try again");
                menu();
        }
    }

    private void setup(){
        String connectionUrl = "jdbc:sqlserver://localhost:host";
        String user = "sa";
        String password = "password";
        String databaseName = "databasse";


        server = new Server(connectionUrl,user,password,databaseName);
        server.setupServer();
        server.createHibernateConfiguration();
        server.buildFactory();
    }

    */
}
