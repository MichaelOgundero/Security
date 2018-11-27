package servlets;

import com.google.appengine.repackaged.com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;
import sec.helper.DbConnect;
import sec.model.Password;
import sec.model.Token;
import sec.model.User;
import sec.model.UserAuthentication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "test", urlPatterns = "/test")
public class testServlest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();


        Connection connection = null;
        try {
            connection = DbConnect.conn();
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement=connection.prepareStatement("INSERT INTO users(email, password, salt)VALUES(?,?,?)");
                preparedStatement.setString(1,"boodp");
                preparedStatement.setString(2,"boo");
                preparedStatement.setString(3,"boo");

                preparedStatement.executeUpdate();
                out.print(HttpStatusCodes.STATUS_CODE_OK);
            }
            catch (SQLException ex) {
                out.print("Check failed (SQL CREATE STATEMENT FAILED");
            }

        } catch (ServletException e) {
            out.print(e);
        }

    }
}
