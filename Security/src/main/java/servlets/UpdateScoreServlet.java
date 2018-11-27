package servlets;

import com.google.appengine.repackaged.com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;
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


@WebServlet(name = "updatescore", urlPatterns = "/updatescore")

public class UpdateScoreServlet extends HttpServlet {

    //userName instead of token
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String email = req.getParameter("email");
        int newScore = Integer.parseInt( req.getParameter("newScore"));

        Gson gson = new Gson();

        if(UserAuthentication.newAccountAuthorization(email)==false)
        {
            User.updateScore(email,newScore);
            out.print(gson.toJson("Success: "+HttpStatusCodes.STATUS_CODE_OK));
        }
        else
        {
            out.print(gson.toJson("Error: "+HttpStatusCodes.STATUS_CODE_UNAUTHORIZED));
        }

    }
}
