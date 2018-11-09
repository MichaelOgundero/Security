import Helper.DbConnect;

import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {

        DbConnect db = new DbConnect();

        ResultSet resultSet =db.connect("SELECT * FROM users");
        try {
            while (resultSet.next())
            {
                System.out.println(resultSet.getString("email"));
            }
        }
        catch (Exception e)
        {

        }

        System.out.println("Hello World!");
    }
}
