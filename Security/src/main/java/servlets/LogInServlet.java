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


@WebServlet(name = "login", urlPatterns = "/login")
public class LogInServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        boolean responseFlag;
        Gson gson = new Gson();
        String json = "";
        if(UserAuthentication.newAccountAuthorization(email)==false)
        {
            responseFlag =UserAuthentication.userAuthenticated(email, password);
            if(responseFlag == true )
            {

                Token token = new Token(email, Token.nextToken());
                while(Token.tokenExists(token.getToken())==true)
                {
                    token = new Token(email, Token.nextToken());
                }
                token.addTokenToDataBase();
                json = gson.toJson(token.getToken());
                out.print(json);
            }
            else
            {
                json = gson.toJson(HttpStatusCodes.STATUS_CODE_UNAUTHORIZED);
                out.print("Error: "+json);
            }
        }
        else
        {
            json = gson.toJson(HttpStatusCodes.STATUS_CODE_NOT_FOUND);
            out.print("Error: "+json);
        }

    }

}
