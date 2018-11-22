package servlets;

import com.google.appengine.repackaged.com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;
import sec.model.Token;
import sec.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "updatescore", urlPatterns = "/updatescore")

public class UpdateScoreServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String token = req.getParameter("token");
        int newScore = Integer.parseInt( req.getParameter("newScore"));

        Gson gson = new Gson();
        if(Token.tokenExists(token)==true)
        {
            User.updateScore(Token.getEmailOfToken(token),newScore);
            out.print("Success: "+HttpStatusCodes.STATUS_CODE_OK);
        }
        else
        {
            out.print("Error: "+HttpStatusCodes.STATUS_CODE_UNAUTHORIZED);
        }

    }
}
