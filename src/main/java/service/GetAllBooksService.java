package service;

import dao.impl.BookDaoImpl;
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


public class GetAllBooksService implements Service {

    private final BookDaoImpl bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        String locale = (String) session.getAttribute(LOCALE);
        if (locale == null) {
            locale = DEFAULT_LOCALE;
        }
        String languageCode = locale.substring(0, 2);
        List<Book> books = bookDao.getAll(languageCode);
        context.setAttribute(BOOKS, books);
        RequestDispatcher dispatcher = request.getRequestDispatcher(ADMIN_BOOKS_URI);
        dispatcher.forward(request, response);
    }
}
