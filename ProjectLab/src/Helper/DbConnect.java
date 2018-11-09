package Helper;
import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.Statement;


public class DbConnect {
    final String instanceConnectionName = "training-project-lab:europe-west3:instance757";
    final String databaseName = "security";
    final String IP_of_instance = "35.198.84.160";
    final String username = "root";
    final String password = "1MerciDieu";

    public ResultSet connect(String query)
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
    }
}
