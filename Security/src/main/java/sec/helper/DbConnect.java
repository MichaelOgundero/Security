package sec.helper;

import javax.servlet.ServletException;
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

    public static Connection getConnection() throws ServletException {
        String url = "jdbc:mysql://google/instance757?cloudSqlInstance=training-project-lab:europe-west3:instance757&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=root&password=khouloud&useSSL=false";
        System.out.println(url);
        try {

            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Error loading Google JDBC Driver", e);
        }
        try {
            Connection conn = DriverManager.getConnection(url);
            conn.setAutoCommit(true);
            return conn;
        } catch (SQLException e) {
            throw new ServletException("SQL Error: " + e.getMessage(), e);
        }
    }

    public static Connection conn() throws ServletException
    {
        try {

            Class.forName("com.mysql.jdbc.GoogleDriver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Error loading Google JDBC Driver", e);
        }
        try {

            String DBUrl = String.format("jdbc:google:mysql://%s/%s",
                    "training-project-lab:europe-west3:instance757", "security");
            Connection  DbConn = DriverManager.getConnection(DBUrl,"root","khouloud");
            return DbConn;
        } catch(SQLException  ex) {
            throw new ServletException("SQL Error: " + ex.getMessage(), ex);
        }

    }
}
