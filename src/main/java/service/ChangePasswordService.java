package service;

import DAO.impl.UserDaoImpl;
import entity.User;

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
        String password = request.getParameter("password").trim();
        System.out.println("pas " + password);
        String newPassword = request.getParameter("new-password").trim();
        System.out.println("newpas " + newPassword);
        String uri = request.getParameter("uri");
        int userId = user.getId();
            int result = userDAO.changePassword(userId, password, newPassword);
        if (result < 1) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?pass-msg=error");
            dispatcher.forward(request, response);
        }
            user = userDAO.getUser(userId);
            session.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?pass-msg=success");
            dispatcher.forward(request, response);
    }
}
