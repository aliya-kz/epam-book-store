package service;

import DAO.BookDao;
import DAO.impl.BookDaoImpl;
import DAO.impl.CartDaoImpl;
import entity.Book;
import entity.Cart;
import entity.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddToCartService implements Service {

    private final CartDaoImpl cartDao = new CartDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int bookId = Integer.parseInt(request.getParameter("id"));
        Cart cart = (Cart) session.getAttribute("cart");
        Map<Book, Integer> items = cart.getCartItems();
        int qty = Integer.parseInt(request.getParameter("qty"));
        String locale = (String) session.getAttribute("locale");
        List<Book> books = bookDao.getAll(locale.substring(0, 2));
        User user = (User) session.getAttribute("user");
        int oldQty = qty;
        for (Book book : books) {
            if (book.getId() == bookId) {
                if (items.containsKey(book)) {
                    oldQty = items.get(book);
                    int newQty = oldQty + qty;
                    if (newQty <= book.getQuantity()) {
                        oldQty = items.replace(book, oldQty + qty);
                        System.out.println(" 1 " + oldQty);
                    } else {
                        oldQty = items.replace(book, book.getQuantity());
                        System.out.println(" 2 " + oldQty);
                    }
                } else {
                    items.put(book, qty);
                }
            }
        }
        if (user != null) {
            cartDao.addToCart(user.getId(), bookId, oldQty);
            cart = cartDao.getCart(user.getId());
            session.setAttribute("cart", cart);
        }
        books = bookDao.getAll(locale.substring(0, 2));
        session.setAttribute("books", books);
        String uri = request.getParameter("uri");
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?msg=added");
        dispatcher.forward(request, response);
    }
}
