package servlets;

import com.google.appengine.repackaged.com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;
import sec.model.User;
import sec.model.UserAuthentication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "deleteaccount", urlPatterns = "/deleteaccount")
public class DeleteAccountServlet extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Gson gson = new Gson();

        if (UserAuthentication.newAccountAuthorization(email)==false) {


            if(UserAuthentication.userAuthenticated(email, password)==true)
            {
                User.deleteAccount(email);
                out.print(gson.toJson( "Success: "+HttpStatusCodes.STATUS_CODE_OK));
            }
            else
            {
                out.print("Error: "+HttpStatusCodes.STATUS_CODE_UNAUTHORIZED);
            }
        }
        else
        {
            out.print("Error: "+HttpStatusCodes.STATUS_CODE_UNAUTHORIZED);
        }
    }
}
