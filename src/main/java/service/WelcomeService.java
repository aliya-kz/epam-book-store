package service;

import DAO.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class WelcomeService implements Service {
    private static SqlDaoFactory factory = SqlDaoFactory.getInstance();
    private LanguageDao languageDao = factory.getLanguageDao();
    private UserDao userDao= factory.getUserDao();
    private BookDao bookDao = factory.getBookDao();
    private CategoryDao categoryDao = factory.getCategoryDao();
    private AuthorDao authorDao = factory.getAuthorDao();
    private FormatDao formatDao = factory.getFormatDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = request.getParameter("locale");
        session.setAttribute("locale", locale);
        String lang = locale.substring(0, 2);
        session.setAttribute("books", bookDao.getAll(lang));
        session.setAttribute("authors", authorDao.getAll(lang));
        session.setAttribute("formats", formatDao.getAll(lang));
        session.setAttribute("categories", categoryDao.getAll(lang));
        session.setAttribute("langs", languageDao.getAll());
        session.setAttribute("users", userDao.getAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index");
        dispatcher.forward(request, response);
    }
}


