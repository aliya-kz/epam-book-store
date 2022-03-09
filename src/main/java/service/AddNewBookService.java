package service;

import DAO.BookDao;
import DAO.impl.BookDaoImpl;
import entity.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static service.GeneralConstants.*;
import static service.ServiceConstants.*;

public class AddNewBookService implements Service {

    private static final BookDao bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String title = request.getParameter(TITLE).trim();
        String bookLanguage = request.getParameter(BOOK_LANGUAGE);
        int formatId = Integer.parseInt(request.getParameter(FORMAT));
        int categoryId = Integer.parseInt(request.getParameter(CATEGORY));
        String[] authIds = request.getParameterValues(AUTHOR_IDS);
        List<Long> authors = new ArrayList<>();
        for (String idString : authIds) {
            long authorId = Integer.parseInt(idString);
            authors.add(authorId);
        }
        String publisher = request.getParameter(PUBLISHER).trim();
        String isbn = request.getParameter(ISBN);
        String priceStr = request.getParameter(PRICE).trim();
        double price = Double.parseDouble(priceStr);
        String qty = request.getParameter(QUANTITY).trim();
        int quantity = Integer.parseInt(qty);
        String description = request.getParameter(DESCRIPTION).trim();

        Part part = null;
        try {
            part = request.getPart(FILE);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        InputStream is = part.getInputStream();
        byte[] bytes = is.readAllBytes();
        Book book = new Book(title, authors, publisher, quantity, price, categoryId, isbn, description, bookLanguage,
                formatId, bytes);
        bookDao.addEntity(book);
        ServiceFactory.getInstance().getService(GET_ALL_BOOKS_SERVICE).execute(request, response);
    }
}