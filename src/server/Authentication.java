package server;

import org.hibernate.query.Query;
import tables.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;
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
    public User logIn(String email, String password){
        User user = returnUser(email);
        return user != null && user.getPassword().equals(Security.authenticatePassword(password,user.getSalt())) ? user : null;
    }

    /**
     * Creates a user, checks if the user already exist.
     * */
    public User createUser(String email, String name, String passwordInsert){
        String firstName, lastName;
        String[] splitName = name.split(" ");
        if(splitName.length < 2){
            return null;
        }
        firstName = splitName[0];
        lastName = splitName[1];
        if(returnUser(email) != null){
            return null;
        }
        System.out.println("Password:");
        Password password = Security.protectPassword(passwordInsert);
        User user = new User(firstName,lastName,email,password.getHash(),password.getSalt());
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        return user;
    }


    private User returnUser(String email){
        List users = session.createQuery("FROM User U WHERE U.email = " + "'" + email + "'").list();
        return users.size() == 0 ? null : (User) users.get(0);
    }

    public void closeSession(){
        session.close();
    }

}
