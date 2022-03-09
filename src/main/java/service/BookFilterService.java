package service;

import dao.BookDao;
import dao.impl.BookDaoImpl;
import entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static service.GeneralConstants.*;


public class BookFilterService implements Service {

    private final BookDao bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE);
        String languageCode = locale.substring(0, 2);
        String[] categories = request.getParameterValues(CATEGORY);
        String[] formats = request.getParameterValues(FORMAT);
        String[] pubLangs = request.getParameterValues(PUBLICATION_LANGUAGE);
        List<Book> filteredBooks = bookDao.getAll(languageCode);
        String reset = request.getParameter(RESET);
        if (reset == null) {
            if (categories != null) {
                long[] categoryIds = Stream.of(categories)
                        .mapToLong(Long::parseLong)
                        .toArray();
                filteredBooks = bookDao.filterByCategory(filteredBooks, categoryIds);
            }
            if (formats != null) {
                long[] formatIds = Stream.of(formats)
                        .mapToLong(Long::parseLong)
                        .toArray();
                filteredBooks = bookDao.filterByFormat(filteredBooks, formatIds);
            }

            if (pubLangs != null) {
                filteredBooks = bookDao.filterByPublLang(filteredBooks, pubLangs);
            }
        }
        session.setAttribute(FILTERED_BOOKS, filteredBooks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/books");
        dispatcher.forward(request, response);
    }
}
