package service;

import DAO.AuthorDao;
import DAO.BookDao;
import DAO.CategoryDao;
import DAO.SqlDaoFactory;
import entity.Author;
import entity.Book;
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
    BookDao bookDao = SqlDaoFactory.getInstance().getBookDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = request.getParameter("locale");
        session.setAttribute("locale", locale);
        String lang = locale.substring(0, 2);
        List<Category> categories = categoryDao.getAll(lang);
        session.setAttribute("categories",categories);
        List<Book> books = bookDao.getAll(lang);
        session.setAttribute("books", books);
        List<Author> authors = authorDao.getAll(lang);
        session.setAttribute("authors", authors);
        String uri = request.getParameter("uri");
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
        dispatcher.forward(request, response);
    }
}
