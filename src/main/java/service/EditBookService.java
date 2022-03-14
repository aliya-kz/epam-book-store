package service;

import dao.BookDao;
import dao.impl.BookDaoImpl;
import entity.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static service.GeneralConstants.*;

public class EditBookService implements Service {

    private final BookDao bookDao = new BookDaoImpl();
    private final HelperClass helperClass = HelperClass.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE);
        String languageCode = locale.substring(0, 2);
        int id = Integer.parseInt(request.getParameter(ID));

        String title = request.getParameter(NEW_TITLE).trim();
        if (title != null && title.length() > 0) {
            bookDao.setColumnValue(BOOKS, id, TITLE, title);
        }

        String[] authIds = request.getParameterValues(NEW_AUTHORS);
        if (authIds != null) {
            List<Integer> authors = new ArrayList<>();
            for (String idString : authIds) {
                int authorId = Integer.parseInt(idString);
                authors.add(authorId);
            }
            bookDao.deleteBookAuthors(id);
            bookDao.setBookAuthors(id, authors);
        }

        String publisher = request.getParameter(NEW_PUBLISHER).trim();
        if (publisher != null && publisher.length() > 0) {
            bookDao.setColumnValue(BOOKS, id, PUBLISHER, publisher);
        }

        String isbn = request.getParameter(NEW_ISBN);
        if (isbn != null && isbn.length() > 0) {
            bookDao.setColumnValue(BOOKS, id, ISBN, isbn);
        }

        String category = request.getParameter(NEW_CATEGORY);
        if (category != null && category.length() > 0) {
            bookDao.setColumnValue(BOOKS, id, CATEGORY_ID, Integer.parseInt(category));
        }

        String priceStr = request.getParameter(NEW_PRICE).trim();
        if (priceStr.length() > 0) {
            double price = Double.parseDouble(priceStr);
            bookDao.setColumnValue(BOOKS, id, PRICE, price);
        }

        String quantityString = request.getParameter(NEW_QUANTITY).trim();
        if (quantityString.length() > 0) {
            int quantity = Integer.parseInt(quantityString);
            bookDao.setColumnValue(BOOKS, id, QUANTITY, quantity);
        }

        String description = request.getParameter(NEW_DESCRIPTION).trim();
        if (description != null && description.length() > 0) {
            bookDao.setColumnValue(BOOKS, id, DESCRIPTION, description);
        }
        helperClass.updateBooksAttribute(session);
        helperClass.forwardToUriWithMessage(request, response, EDIT_BOOK_URI, ID + SIGN_EQUALS + id);
    }
}
