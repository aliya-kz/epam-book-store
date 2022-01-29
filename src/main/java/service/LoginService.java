package service;

import DAO.*;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



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
        String uri = request.getRequestURI();
        String locale = (String) session.getAttribute("locale");
        String lang = locale.substring(0, 2);
        User user = userDao.validateUser(email, password);
        if (user.getName() == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/logIn.jsp?msg=wrong");
            dispatcher.forward(request, response);
        } else {
            if (user.isBlocked()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/logIn.jsp?msg=blocked");
                dispatcher.forward(request, response);
            }
            session.setAttribute("books", bookDao.getAll(lang));
            session.setAttribute("authors", authorDao.getAll(lang));
            session.setAttribute("formats", formatDao.getAll(lang));
            session.setAttribute("categories", categoryDao.getAll(lang));
            session.setAttribute("langs", languageDao.getAll());
            if (user.isAdmin()) {
                session.setAttribute("users", userDao.getAll());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/welcomeAdmin.jsp");
                dispatcher.forward(request, response);
            } else {
                session.setAttribute("user", user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}

