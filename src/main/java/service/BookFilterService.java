package service;

import DAO.BookDao;
import DAO.impl.BookDaoImpl;
import entity.Book;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;


public class BookFilterService implements Service {

    private final BookDao bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute("locale");
        String lang = locale.substring(0, 2);
        String []categories = request.getParameterValues("category");
        String[] formats = request.getParameterValues("format");
        String [] pubLangs = request.getParameterValues("publang");
        List<Book> filteredBooks = bookDao.getAll(lang);
        String reset = request.getParameter("reset");
        if (reset == null) {
            if (categories != null) {
                int[] categoryIds = Stream.of(categories)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                filteredBooks = bookDao.filterByCategory(filteredBooks, categoryIds);
            }
            if (formats != null) {
                int[] formatIds = Stream.of(formats)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                filteredBooks = bookDao.filterByFormat(filteredBooks, formatIds);
            }

            if (pubLangs != null) {
                filteredBooks = bookDao.filterByPublLang(filteredBooks, pubLangs);
            }
        }
        session.setAttribute("filteredBooks", filteredBooks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/books");
        dispatcher.forward(request, response);
    }
}
