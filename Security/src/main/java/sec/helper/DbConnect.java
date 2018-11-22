package sec.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {

    public static Connection connect()
    {
        Connection connection=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String user="root";
            String password="";
            String url="jdbc:mysql://localhost/projectLab";

            connection=(Connection) DriverManager.getConnection(url,user,password);
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Connection failed (ClassNotFoundException)");
        }
        catch (SQLException ex) {
            System.out.println("Connection failed (SQLException)");
        }

        System.out.println(connection);

        return connection;
    }

}
