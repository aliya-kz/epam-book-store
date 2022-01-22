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
    private UserDaoImpl userDAO = new UserDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        PasswordEncrypter passwordEncrypter = new PasswordEncrypter();
        String email = (String) session.getAttribute("email");
        String password = request.getParameter("password");
        String encryptedPassword = passwordEncrypter.encrypt(password);
        String newPassword = request.getParameter("new_password");
        String newEncryptedPassword = passwordEncrypter.encrypt(newPassword);
        User user = new User(email, encryptedPassword);
        User userExists = userDAO.validateUser(email, encryptedPassword);
        if (userExists != null) {
            userDAO.changePassword(user, newEncryptedPassword);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/view/welcome.jsp");
            dispatcher.forward(request, response);
        }
        else {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
