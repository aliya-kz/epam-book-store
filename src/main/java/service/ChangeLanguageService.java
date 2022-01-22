package service;

import DAO.AuthorDao;
import DAO.CategoryDao;
import DAO.SqlDaoFactory;

import entity.Author;
import entity.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ChangeLanguageService implements Service {
    CategoryDao categoryDao = SqlDaoFactory.getInstance().getCategoryDao();
    AuthorDao authorDao = SqlDaoFactory.getInstance().getAuthorDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String lang = request.getParameter("lang");
        HttpSession session = request.getSession();
        Locale locale = new Locale(lang);
        session.setAttribute("locale", locale);
        List<Category> categories = categoryDao.getAll(lang);
        session.setAttribute("categories",categories);
        List<Author> authors = authorDao.getAll(lang);
        session.setAttribute("authors", authors);

        String uri = request.getParameter("uri");
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
        dispatcher.forward(request, response);
    }
}
