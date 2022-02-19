package service;

import DAO.AuthorDao;
import DAO.BookDao;
import DAO.impl.AuthorDaoImpl;
import DAO.impl.BookDaoImpl;
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


public class EditImageService implements Service {

    private final AuthorDao authorDao = new AuthorDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute("locale");
        String lang = locale.substring(0, 2);
        int id = Integer.parseInt(request.getParameter("id"));
        String table = request.getParameter("table");
        Part part = null;
        try {
            part = request.getPart("file");
        } catch (ServletException e) {
            e.printStackTrace();
        }
        InputStream is = ((Part) part).getInputStream();
        byte[] bytes = is.readAllBytes();
        if (table.equals("authors")) {
            authorDao.setColumnValue(table, id, "image", bytes);
            List<Author> authors= authorDao.getAll(lang);
            session.setAttribute("authors", authors);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/editAuthor.jsp?author_id=" + id);
            dispatcher.forward(request, response);
        } else if (table.equals("book_covers")) {
            bookDao.setColumnValue(table, id, "image", bytes);
            List<Book> books= bookDao.getAll(lang);
            session.setAttribute("books", books);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/editBook.jsp?author_id=" + id);
            dispatcher.forward(request, response);
        }
    }
}

