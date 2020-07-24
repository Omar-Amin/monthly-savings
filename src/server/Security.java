package server;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Security {

    private static String generateSalt(){
        Random random = new Random();
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 128; i++) {
            int type = random.nextInt(3) + 1;
            char c;
            if(type == 1){
                c = (char) (random.nextInt((122-97)+1) + 97);
            }else if(type == 2){
                c = (char) (random.nextInt((90-65)+1) + 65);
            }else {
                c = (char) (random.nextInt((57-48)+1) + 48);
            }
            salt.append(c);
        }
        return salt.toString();
    }

    private static String hashPassword(String password){
        return Hashing.sha512().hashString(password, StandardCharsets.UTF_8).toString();
    }

    /**
     * Used when creating an account. The user gives a password, the method
     * generates salt and appends it to the password, then hash the whole string.
     * */
    static Password protectPassword(String password){
        String salt = generateSalt();
        String hash = hashPassword(password + salt);
        return new Password(hash,salt);
    }

    /**
     * From the user input, the method hashes the password with the salt,
     * to check if the hashvalue is the same as what's stored in the DB.
     * */
    static String authenticatePassword(String password, String salt){
        return hashPassword(password+salt);
    }

}
