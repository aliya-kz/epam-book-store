package service;

import DAO.impl.UserDaoImpl;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static service.GeneralConstants.*;


public class ChangePasswordService implements Service {

    private final UserDaoImpl userDAO = new UserDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String password = request.getParameter(PASSWORD).trim();
        String newPassword = request.getParameter(NEW_PASSWORD).trim();
        String uri = request.getParameter(URI);
        long userId = user.getId();
        int result = userDAO.changePassword(userId, password, newPassword);
        if (result < 1) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?pass-msg=error");
            dispatcher.forward(request, response);
        }
        user = userDAO.getUser(userId);
        session.setAttribute(USER, user);
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?pass-msg=success");
        dispatcher.forward(request, response);
    }
}
