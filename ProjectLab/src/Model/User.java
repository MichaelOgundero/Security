package Model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User {


    private String name, lastName;
    private String email;
    char[] password;
    byte[] salt;


    private final SecureRandom random;

    public User(String name, String lastName,String email, char[] password, byte[] salt)
    {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        random = new SecureRandom();

    }

    //true : sucessful update
    //false : update failed
    public static boolean updatePassword(String email, String newPassword, String currentPassword)
    {
        //DB Connect
        //retrieve salt
        byte[] salt = null;
        //retrieve current password
        char[] password = null;
        char[] passedPassword = currentPassword.toCharArray();

        if(Password.isExpectedPassword(password, salt, passedPassword)==true)
        {
            //update password
        }
        else
        {
            return false;
        }
     return false;
    }


    public static void addNewUserToDataBase(User user){
        //DB connection
        //load user into database
    }



}
