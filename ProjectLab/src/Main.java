import Helper.DbConnect;
import Model.SignUpController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {




        SignUpController.signUp("ilefHammemi@gmail.com","khouloud".toCharArray(),"ilef","hammemi");



    }
}
