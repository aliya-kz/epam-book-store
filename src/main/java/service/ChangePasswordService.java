package service;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.User;
import validation.ChangePasswordFormValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

import static service.GeneralConstants.*;
import static validation.ValidationConstants.CONTENT;


public class ChangePasswordService implements Service {

    private ChangePasswordFormValidator validator;
    private static HelperClass helperClass = HelperClass.getInstance();
    private final UserDao userDao = new UserDaoImpl();
    ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE);
        User user = (User) session.getAttribute(USER);
        long userId = user.getId();
        String password = request.getParameter(PASSWORD).trim();
        String newPassword = request.getParameter(NEW_PASSWORD).trim();
        String newPasswordRepeat = request.getParameter(NEW_PASSWORD_REPEAT).trim();
        String uri = request.getParameter(URI);
        validator = new ChangePasswordFormValidator(locale);
        boolean inputsValid = validator.isValid(user, password, newPassword, newPasswordRepeat);
        String message;
        if (!inputsValid) {
            message = validator.getMessage();
        } else {
            message = resourceBundle.getString(PASSWORD_CHANGED);
            userDao.changePassword(user.getId(), password, newPassword);
            helperClass.updateUserAttribute(session, userId);
        }
        request.setAttribute(CHANGE_PASSWORD_MESSAGE, message);
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
        dispatcher.forward(request, response);
    }
}
