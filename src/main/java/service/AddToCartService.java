package service;

import dao.BookDao;
import dao.impl.BookDaoImpl;
import dao.impl.CartDaoImpl;
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

import static service.GeneralConstants.*;


public class AddToCartService implements Service {

    private final CartDaoImpl cartDao = new CartDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int bookId = Integer.parseInt(request.getParameter(ID));
        Cart cart = (Cart) session.getAttribute(CART);
        Map<Book, Integer> items = cart.getCartItems();
        int quantity = Integer.parseInt(request.getParameter(QUANTITY));
        String locale = (String) session.getAttribute(LOCALE);
        String languageCode = locale.substring(0, 2);
        List<Book> books = bookDao.getAll(languageCode);
        User user = (User) session.getAttribute(USER);
        int oldQty = quantity;
        for (Book book : books) {
            if (book.getId() == bookId) {
                if (items.containsKey(book)) {
                    oldQty = items.get(book);
                    int newQty = oldQty + quantity;
                    if (newQty <= book.getQuantity()) {
                        oldQty = items.replace(book, oldQty + quantity);
                    } else {
                        oldQty = items.replace(book, book.getQuantity());
                    }
                } else {
                    items.put(book, quantity);
                }
            }
        }


        if (user != null) {
            cartDao.addToCart(user.getId(), bookId, oldQty);
            cart = cartDao.getCart(user.getId());
            session.setAttribute(CART, cart);
        }
        books = bookDao.getAll(locale.substring(0, 2));
        session.setAttribute(BOOKS, books);
        String uri = request.getParameter(URI);
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?" + MESSAGE + "=" + ADDED);
        dispatcher.forward(request, response);
    }
}
