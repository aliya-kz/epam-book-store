package service;

import DAO.*;
import DAO.impl.AuthorDaoImpl;
import DAO.impl.BookDaoImpl;
import DAO.impl.CategoryDaoImpl;
import DAO.impl.FormatDaoImpl;
import entity.Author;
import entity.Book;
import entity.Category;
import entity.Format;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static service.GeneralConstants.*;


public class ChangeLanguageService implements Service {

    private final CategoryDao categoryDao = new CategoryDaoImpl();
    private final AuthorDao authorDao = new AuthorDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final FormatDao formatDao = new FormatDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = request.getParameter(LOCALE);
        session.setAttribute(LOCALE, locale);
        String lang = locale.substring(0, 2);
        List<Category> categories = categoryDao.getAll(lang);
        session.setAttribute(CATEGORIES, categories);
        List<Book> books = bookDao.getAll(lang);
        session.setAttribute(BOOKS, books);
        List<Author> authors = authorDao.getAll(lang);
        session.setAttribute(AUTHORS, authors);
        List<Format> formats = formatDao.getAll(lang);
        session.setAttribute(FORMATS, formats);
        String uri = request.getParameter(URI);
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
        dispatcher.forward(request, response);
    }
}
