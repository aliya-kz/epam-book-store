package service;

import dao.AuthorDao;
import dao.BookDao;
import dao.impl.AuthorDaoImpl;
import dao.impl.BookDaoImpl;
import entity.Author;
import entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static service.GeneralConstants.*;


public class EditImageService implements Service {

    private final AuthorDao authorDao = new AuthorDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private static HelperClass helperClass = HelperClass.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        String locale = (String) session.getAttribute(LOCALE);
        String languageCode = locale.substring(0, 2);
        int id = Integer.parseInt(request.getParameter(ID));
        String table = request.getParameter(TABLE);
        byte[] bytes = helperClass.partToBytes(request, FILE);
        if (table.equals(AUTHORS)) {
            authorDao.setColumnValue(table, id, IMAGE, bytes);
            List<Author> authors = authorDao.getAll(languageCode);
            context.setAttribute(AUTHORS, authors);
            RequestDispatcher dispatcher = request.getRequestDispatcher(EDIT_AUTHOR_URI + SIGN_QUESTION + AUTHOR_ID + SIGN_EQUALS + id);
            dispatcher.forward(request, response);
        } else if (table.equals(BOOK_COVERS)) {
            bookDao.setColumnValue(table, id, IMAGE, bytes);
            List<Book> books = bookDao.getAll(languageCode);
            context.setAttribute(BOOKS, books);
            RequestDispatcher dispatcher = request.getRequestDispatcher(EDIT_BOOK_URI + SIGN_QUESTION + BOOK_ID + SIGN_EQUALS + id);
            dispatcher.forward(request, response);
        }
    }
}

