package server;

import org.hibernate.query.Query;
import tables.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class Authentication {

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
        // Doesn't make a fair split, if the user has a middle name
        // it ignores the last name and takes the middle name as lastname
        // (not necessary to fix at the moment)
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
