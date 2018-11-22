package servlets;

import com.google.appengine.repackaged.com.google.api.client.http.HttpStatusCodes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "test", urlPatterns = "/test")
public class testServlest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String unitID = req.getParameter("unitID");
        int unitIdAsInt = Integer.parseInt(unitID);

        PrintWriter out = resp.getWriter();
        out.print(HttpStatusCodes.STATUS_CODE_OK);
    }
}
