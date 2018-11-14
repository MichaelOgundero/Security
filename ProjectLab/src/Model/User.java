package Model;

import Helper.DbConnect;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.*;
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
        DbConnect db = new DbConnect();

        Connection connection = db.connect();

        PreparedStatement preparedStatement=null;

        try {
            preparedStatement=connection.prepareStatement("INSERT INTO users(gmail, password, salt)VALUES(?,?,?)");

            preparedStatement.setString(1,user.email);
            preparedStatement.setString(2,new String(user.password));
            preparedStatement.setString(3,new String(user.salt));

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO user_information(gmail, name, last_name)VALUES (?,?,?)");

            preparedStatement.setString(1,user.email);
            preparedStatement.setString(2, user.name);
            preparedStatement.setString(3,user.lastName);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }


    }

    public static void deleteAccount(String email)
    {
        DbConnect db = new DbConnect();

        Connection connection = db.connect();

        PreparedStatement preparedStatement=null;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE gmail = ?");
            preparedStatement.setString(1,email);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM user_information WHERE gmail = ?");
            preparedStatement.setString(1,email);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("DELETE FROM token WHERE gmail = ?");
            preparedStatement.setString(1,email);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }

    }

    public static List<String> activeUsers()
    {
        ArrayList<String> activeUsers= new ArrayList<String>();
        DbConnect db = new DbConnect();

        Connection connection = db.connect();

        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connection.prepareStatement("SELECT gmail FROM token");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                activeUsers.add(resultSet.getString("gmail"));
            }

        } catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }


        return activeUsers;

    }

}
