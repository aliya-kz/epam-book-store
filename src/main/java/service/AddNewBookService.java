package service;

import DAO.BookDao;
import DAO.impl.BookDaoImpl;
import entity.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddNewBookService implements Service {

    private static final BookDao bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute("locale");
        String lang = locale.substring(0, 2);
        String title = request.getParameter("title").trim();
        String bookLanguage = request.getParameter("language");
        int formatId = Integer.parseInt(request.getParameter("format"));
        int categoryId = Integer.parseInt(request.getParameter("category"));
        String authIds[] = request.getParameterValues("author_ids");
        List<Integer> authors = new ArrayList<>();
            for (String idString : authIds) {
                int authorId = Integer.parseInt(idString);
                authors.add(authorId);
            }
        String publisher = request.getParameter("publisher").trim();
        String isbn = request.getParameter("isbn");
        String priceStr = request.getParameter("price").trim();
        double price = Double.parseDouble(priceStr);
        String qty = request.getParameter("quantity").trim();
        int quantity = Integer.parseInt(qty);
        String description = request.getParameter("description").trim();

        Part part = null;
        try {
            part = request.getPart("file");
        } catch (ServletException e) {
            e.printStackTrace();
        }
        InputStream is = ((Part) part).getInputStream();
        byte[] bytes = is.readAllBytes();
        Book book = new Book(title, authors, publisher, quantity, price, categoryId, isbn, description, bookLanguage,
                formatId, bytes);
        bookDao.addEntity(book);
        ServiceFactory.getInstance().getService("get_all_books").execute(request, response);
    }
}