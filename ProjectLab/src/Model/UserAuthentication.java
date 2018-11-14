package Model;

import Helper.DbConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuthentication {

    //false user non authenticated
    // true authenticated
    public static boolean userAuthenticated(String email, String password)
    {

        DbConnect db = new DbConnect();
        Connection connection = db.connect();
        PreparedStatement preparedStatement = null;
         char[] user_password = null;
         byte[]user_salt = null;
         try
         {
             preparedStatement = connection.prepareStatement("SELECT password, salt FROM users WHERE gmail =? ");
             preparedStatement.setString(1, email);

             ResultSet resultSet =  preparedStatement.executeQuery();
             while (resultSet.next())
             {
                 user_password = resultSet.getString("password").toCharArray();
                 user_salt = resultSet.getString("salt").getBytes();
             }
         }
         catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED)");
         }

        char[] passwordToCheck = password.toCharArray();

        if(Password.isExpectedPassword(passwordToCheck, user_salt, user_password))
        {
            return true;
        }
        else
            return false;
    }

    public static Boolean userAuthorized(Token token)
    {
        if (Token.tokenExists(token))
        {
            return true;
        }
        else
            return false;
    }


    public static boolean newAccountAuthorization(String email)
    {
        //DB connection
        //SELECT count(email) FROM users
        //WHERE email = 'email'
        // if count(email) == 0
        //return true;
        //else return false;

        DbConnect db = new DbConnect();

        Connection connection = db.connect();

        PreparedStatement preparedStatement=null;
        ResultSet result=null;
        int count=0;
        try {
            preparedStatement=connection.prepareStatement("SELECT count(gmail) AS boo FROM users WHERE gmail=?;");

            preparedStatement.setString(1,email);
            result=preparedStatement.executeQuery();

            while (result.next())
            {
                count = Integer.parseInt( result.getString("boo"));
                System.out.println(count);
            }


        } catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED)");
        }


    if (count==0)
        return true;
    else
        return false;
    }
}
