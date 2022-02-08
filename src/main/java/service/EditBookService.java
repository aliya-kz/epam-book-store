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
import java.util.ArrayList;
import java.util.List;

public class EditBookService implements Service{

    BookDao bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute("locale");
        String lang = locale.substring(0, 2);
        int id = Integer.parseInt(request.getParameter("id"));

        String title = request.getParameter("new_title").trim();
        if (title != null && title.length()>0) {
            bookDao.setColumnValue("books", id, "title", title);
        }

        String authIds[] = request.getParameterValues("new_authors");
        if (authIds != null) {
            List<Integer> authors = new ArrayList<>();
            for (String idString : authIds) {
                int authorId = Integer.parseInt(idString);
                authors.add(authorId);
            }
            bookDao.deleteBookAuthors(id);
            bookDao.setBookAuthors(id, authors);
        }

        String publisher = request.getParameter("new_publisher").trim();
        if (publisher != null && publisher.length() > 0) {
            bookDao.setColumnValue("books", id, "publisher", publisher);
        }

        String isbn = request.getParameter("new_isbn");
        if (isbn != null && isbn.length() > 0) {
            bookDao.setColumnValue("books", id, "isbn", isbn);
        }

        String category = request.getParameter("new_category");
        if (category != null && category.length()>0) {
            bookDao.setColumnValue("books", id, "category_id", Integer.parseInt(category));
        }

        String priceStr = request.getParameter("new_price").trim();
        if (priceStr.length() > 0) {
           int price = Integer.parseInt(priceStr);
            bookDao.setColumnValue("books", id, "price", price);
        }

        String qty = request.getParameter("new_quantity").trim();
        if (qty.length() > 0)   {
            int quantity = Integer.parseInt(qty);
            bookDao.setColumnValue("books", id, "quantity", quantity);
        }

        String description = request.getParameter("new_description").trim();
        if (description != null && description.length() > 0) {
            bookDao.setColumnValue("books", id, "description", description);
        }
        List<Book> books = bookDao.getAll(lang);
        session.setAttribute("books", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-book?id=" + id);
        dispatcher.forward(request, response);
    }
}
