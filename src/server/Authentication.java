package server;

import tables.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Scanner;

public class Authentication {
    private final Scanner sc = new Scanner(System.in);
    private final Session session;

    public Authentication(Server server){
        this.session = server.getSession();
    }

    /**
     * Logs in if the email and password is correct.
     * Identical emails cannot exist. (because of how the user is created)
     * */
    public User logIn(){
        System.out.println("email:");
        String email = sc.next();
        System.out.println("password:");
        String password = sc.next();
        User user = returnUser(email);
        session.close();
        return user != null && user.getPassword().equals(Security.authenticatePassword(password,user.getSalt())) ? user : null;
    }

    /**
     * Creates a user, checks if the user already exist.
     * */
    public User createUser(){
        String firstName, lastName, email;
        System.out.println("First name:");
        firstName = sc.next();

        System.out.println("Last name");
        lastName = sc.next();

        System.out.println("Email:");
        email = sc.next();

        if(returnUser(email) != null){
            System.out.println("Account already exist");
            return null;
        }

        System.out.println("Password:");
        Password password = Security.protectPassword(sc.next());
        User user = new User(firstName,lastName,email,password.getHash(),password.getSalt());
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        return user;
    }


    private User returnUser(String email){
        Criteria criteria = session.createCriteria(User.class);
        return (User) criteria.add(Restrictions.eq("email", email))
                .uniqueResult();
    }

}
