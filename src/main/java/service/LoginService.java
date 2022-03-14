package service;

import dao.*;
import dao.impl.*;
import entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


import static service.GeneralConstants.*;


public class LoginService implements Service {

    private final UserDao userDao = new UserDaoImpl();
    private final HelperClass helperClass = HelperClass.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();
        boolean userIsValid = userDao.validateUser(email, password);
        if (!userIsValid) {
            helperClass.forwardToUriWithMessage(request, response,LOGIN_URI, WRONG);
        } else {
            long userId = userDao.getIdByEmail(email);
            User user = userDao.getUser(userId);
            if (user.isBlocked()) {
                helperClass.forwardToUriWithMessage(request, response,LOGIN_URI, BLOCKED);
            } else if (user.isAdmin()) {
                helperClass.updateAllUsersAttribute(session);
                RequestDispatcher dispatcher = request.getRequestDispatcher(ADMIN_BOOKS_URI);
                dispatcher.forward(request, response);
            } else {
                helperClass.updateUserAttributes(session, userId);
                RequestDispatcher dispatcher = request.getRequestDispatcher(INDEX_URI);
                dispatcher.forward(request, response);
            }
        }
    }
}

