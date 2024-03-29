package servlets;

import data.Credentials;
import models.User;
import models.UserRole;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login/admin")
public class AdminLoginServlet extends HttpServlet {
    private Credentials credentials;

    @Override
    public void init() throws ServletException {
        super.init();
        credentials = Credentials.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null || password == null) {
            response.sendError(400);
            return;
        }
        User user = credentials.getUser(login);
        if (user == null || !user.getPassword().equals(password)) {
            response.sendRedirect("/loginFailed.html");
            return;
        }
        if (!user.getUserRole().equals(UserRole.Admin)) {
            response.sendError(403);
        }
        ServletContext context = getServletContext();
        context.setAttribute("user", user);
        response.sendRedirect("/admin");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
