package sec.model;

import sec.helper.DbConnect;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class UserAuthentication {

    //false user non authenticated
    // true authenticated
    public static boolean userAuthenticated(String email, String password)
    {

        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement = null;
        String user_password = null;
        byte[] user_salt = null;
        try
        {
            preparedStatement = connection.prepareStatement("SELECT password, salt FROM users WHERE email =? ");
            preparedStatement.setString(1, email);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next())
            {
                user_password = resultSet.getString("password");
                user_salt = Base64.getDecoder().decode(resultSet.getString("salt").getBytes());
            }
        }
        catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED)");
        }

        String passwordToCheck =Base64.getEncoder().encodeToString( (new String(Password.hash(password.toCharArray(), user_salt))).getBytes());

        if(passwordToCheck.equals(user_password))
        {
            return true;
        }
        else
            return false;
    }

    public static Boolean userAuthorized(String token)
    {
        if (Token.tokenExists(token))
        {
            return true;
        }
        else
            return false;
    }


    //registration
    public static boolean newAccountAuthorization(String email)
    {

        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement=null;
        ResultSet result=null;
        int count=0;
        try {
            preparedStatement=connection.prepareStatement("SELECT count(email) AS boo FROM users WHERE email=?;");

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
