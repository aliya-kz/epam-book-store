package service;

import DAO.*;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;


public class LoginService implements Service {
    private static SqlDaoFactory factory = SqlDaoFactory.getInstance();
    private LanguageDao languageDao = factory.getLanguageDao();
    private UserDao userDao= factory.getUserDao();
    private BookDao bookDao = factory.getBookDao();
    private CategoryDao categoryDao = factory.getCategoryDao();
    private AuthorDao authorDao = factory.getAuthorDao();
    private FormatDao formatDao = factory.getFormatDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute("locale");
        User user = userDao.validateUser(email, password);
        if (user == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login/msg=error");
            dispatcher.forward(request, response);
        } else {
            session.setAttribute("books", bookDao.getAll());
            session.setAttribute("authors", authorDao.getAll(locale));
            session.setAttribute("formats", formatDao.getAll());
            session.setAttribute("categories", categoryDao.getAll(locale));
            session.setAttribute("langs", languageDao.getAll());
            if (user.isAdmin()) {
                session.setAttribute("users", userDao.getAll());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/welcomeAdmin.jsp");
                dispatcher.forward(request, response);
            } else {
                session.setAttribute("current_user", user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index");
                dispatcher.forward(request, response);
            }
        }
    }
}

