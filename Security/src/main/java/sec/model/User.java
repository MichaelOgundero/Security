package sec.model;

import sec.helper.DbConnect;

import javax.servlet.ServletException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class User {
    private String name, lastName;
    private String email;
    char[] password;
    byte[] salt;
    int score;

    private SecureRandom random ;

    public char[] getPassword()
    {
        return password;
    }

    public byte[] getSalt()
    {
        return salt;
    }

    public User(String name, String lastName,String email, char[] password, byte[] salt)
    {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        random = new SecureRandom();

    }

    public User(String name, String lastName, String email, int score)
    {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.score = score;
    }

    public static void addNewUserToDataBase( User user) {
        //DB connection
        //load user into database
        Connection connection = null;
        try {

            connection = DbConnect.conn();

        }
        catch(ServletException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
        PreparedStatement preparedStatement=null;

        try {

            preparedStatement=connection.prepareStatement("INSERT INTO users(email, password, salt)VALUES(?,?,?)");

            preparedStatement.setString(1,user.email);
            String temPass = new String( Password.hash(user.password, user.salt));
            preparedStatement.setString(2,Base64.getEncoder().encodeToString(temPass.getBytes()));
            preparedStatement.setString(3, Base64.getEncoder().encodeToString(user.salt));

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO user_Information(user_email, user_name, last_name, score) VALUES(?,?,?,?)");

            preparedStatement.setString(1,user.email);
            preparedStatement.setString(2, user.name);
            preparedStatement.setString(3,user.lastName);
            preparedStatement.setInt(4,0);

            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }


    }



    //DELETES FROM users, user_information and token tables
    public static void deleteAccount(String email)
    {
        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement=null;

        try {
            preparedStatement = connection.prepareStatement("DELETE FROM tokens WHERE user_email = ?");
            preparedStatement.setString(1,email);
            preparedStatement.executeUpdate();


            preparedStatement = connection.prepareStatement("DELETE FROM user_Information WHERE user_email = ?");
            preparedStatement.setString(1,email);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE email = ?");
            preparedStatement.setString(1,email);
            preparedStatement.executeUpdate();




        } catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }

    }


    public static byte[] getUserSalt(String email) {

        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        byte[] user_salt = null;
        PreparedStatement preparedStatement=null;

        try {
            preparedStatement = connection.prepareStatement("SELECT salt FROM users WHERE email = ?");
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                user_salt = resultSet.getString("salt").getBytes();
            }
            return user_salt;
        }
        catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }
        return user_salt;
    }



    public static boolean checkPassword(String email, String passedPassword)
    {
        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        char[] real_password = null;
        PreparedStatement preparedStatement=null;

        try {
            preparedStatement = connection.prepareStatement("SELECT salt FROM users WHERE email = ?");
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                real_password = resultSet.getString("salt").toCharArray();
            }

            byte[] user_salt = getUserSalt(email);

            if(Password.isExpectedPassword(passedPassword.toCharArray(), user_salt, real_password)==true)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }

        return false;
    }


    public static ArrayList<User> activeUsers()
    {
        ArrayList<String> activeUsersEmails= new ArrayList<String>();
        ArrayList<User> activeUsers = new ArrayList<User>();
        ArrayList<String> ii = new ArrayList<String>();

        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connection.prepareStatement("SELECT user_email FROM tokens");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                activeUsersEmails.add(resultSet.getString("user_email"));
            }

            for (String email: activeUsersEmails) {
                preparedStatement = connection.prepareStatement("SELECT * FROM user_Information WHERE user_email = ?");
                preparedStatement.setString(1, email);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    activeUsers.add(new User(resultSet.getString("user_name"), resultSet.getString("last_name"),
                            resultSet.getString("user_email"), resultSet.getInt("score")));
                    ii.add(resultSet.getString("user_name"));

                }
            }

        } catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }


        return activeUsers;

    }



    public static void updateScore(String email,int newScore)
    {
        Connection connection = null;
        try {
            connection = DbConnect.conn();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement=null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE user_Information SET score =? WHERE user_email=?");
            preparedStatement.setInt(1,newScore);
            preparedStatement.setString(2, email);

            preparedStatement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }
    }


}
