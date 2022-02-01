package service;

import DAO.impl.UserDaoImpl;
import entity.User;
import passwordEncr.PasswordEncrypter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangePasswordService implements Service {
    private final UserDaoImpl userDAO = new UserDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();
        String password = request.getParameter("password").trim();
        String newPassword = request.getParameter("new_password").trim();
        String uri = (String)request.getAttribute("uri");
        int userId = userDAO.validateUser(email, password);
        if (userId > 0) {
            userDAO.changePassword(user.getId(), newPassword);
            user = userDAO.getUser(userId);
            session.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?pass-msg=success");
            dispatcher.forward(request, response);
        }
        else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?pass-msg=error");
            dispatcher.forward(request, response);
        }
    }
}
