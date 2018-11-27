package servlets;

import com.google.appengine.repackaged.com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;
import sec.model.Token;
import sec.model.UserAuthentication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "logout", urlPatterns = "/logout")
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String token = req.getParameter("token");
       
        Gson gson = new Gson();

        if(Token.tokenExists(token))
        {
            Token.deleteToken(token);
            out.print(gson.toJson("Success: "+HttpStatusCodes.STATUS_CODE_OK));
        }
        else
        {
            out.print(gson.toJson("Error: "+HttpStatusCodes.STATUS_CODE_UNAUTHORIZED));

        }

    }

}
