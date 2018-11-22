package sec.model;

import sec.helper.DbConnect;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String name, lastName;
    private String email;
    char[] password;
    byte[] salt;
    int score;

    private SecureRandom random ;

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

    //DELETES FROM users, user_information and token tables
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


    private static byte[] getUserSalt(String email) {

        DbConnect db = new DbConnect();

        Connection connection = db.connect();
        byte[] user_salt = null;
        PreparedStatement preparedStatement=null;

        try {
            preparedStatement = connection.prepareStatement("SELECT salt FROM users WHERE gmail = ?");
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
        DbConnect db = new DbConnect();

        Connection connection = db.connect();
        char[] real_password = null;
        PreparedStatement preparedStatement=null;

        try {
            preparedStatement = connection.prepareStatement("SELECT salt FROM users WHERE gmail = ?");
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


    public static List<User> activeUsers()
    {
        ArrayList<String> activeUsersEmails= new ArrayList<String>();
        ArrayList<User> activeUsers = new ArrayList<User>();
        DbConnect db = new DbConnect();

        Connection connection = db.connect();

        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connection.prepareStatement("SELECT gmail FROM token");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                activeUsersEmails.add(resultSet.getString("gmail"));
            }

            for (String email: activeUsersEmails) {
                preparedStatement = connection.prepareStatement("select * from user_information where email = ?");
                preparedStatement.setString(1, email);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    activeUsers.add(new User(resultSet.getString("name"), resultSet.getString("lastName"),
                            resultSet.getString("email"), Integer.parseInt(resultSet.getString("score"))));
                }

            }

        } catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }


        return activeUsers;

    }



    public static void updateScore(String email,int newScore)
    {
        DbConnect db = new DbConnect();

        Connection connection = db.connect();

        PreparedStatement preparedStatement=null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE user_information set score =? where gmail=?");
            preparedStatement.setInt(1,newScore);
            preparedStatement.setString(2, email);

            preparedStatement.executeUpdate();

        }catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }
    }


}
