package service;

import dao.AuthorDao;
import dao.BookDao;
import dao.impl.AuthorDaoImpl;
import dao.impl.BookDaoImpl;
import entity.Author;
import entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static service.GeneralConstants.*;


public class EditImageService implements Service {

    private final AuthorDao authorDao = new AuthorDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE);
        String languageCode = locale.substring(0, 2);
        int id = Integer.parseInt(request.getParameter(ID));
        String table = request.getParameter(TABLE);
        Part part = null;
        try {
            part = request.getPart(FILE);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        InputStream is = ((Part) part).getInputStream();
        byte[] bytes = is.readAllBytes();
        if (table.equals(AUTHORS)) {
            authorDao.setColumnValue(table, id, IMAGE, bytes);
            List<Author> authors = authorDao.getAll(languageCode);
            session.setAttribute(AUTHORS, authors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/editAuthor.jsp?author_id=" + id);
            dispatcher.forward(request, response);
        } else if (table.equals(BOOK_COVERS)) {
            bookDao.setColumnValue(table, id, IMAGE, bytes);
            List<Book> books = bookDao.getAll(languageCode);
            session.setAttribute(BOOKS, books);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/editBook.jsp?author_id=" + id);
            dispatcher.forward(request, response);
        }
    }
}

