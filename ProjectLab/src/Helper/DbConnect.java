package Helper;
import java.io.Console;
import java.io.IOException;
import java.sql.*;


public class DbConnect {
    /*final String instanceConnectionName = "training-project-lab:europe-west3:instance757";
    final String databaseName = "security";
    final String IP_of_instance = "35.198.84.160";
    final String username = "root";
    final String password = "1MerciDieu";*/

   /* public ResultSet connect(String query)
    { final String jdbcUrl = String.format(
            "jdbc:mysql://google/%s?cloudSqlInstance=%s" +
                    "&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=%s&password=%s" +
                    "&useSSL=false",
            databaseName,
            instanceConnectionName,
            username,
            password);
    // );
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                return resultSet;
            }catch(Exception e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }*/

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
