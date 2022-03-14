package service;

import dao.BookDao;
import dao.CartDao;
import dao.impl.BookDaoImpl;
import dao.impl.CartDaoImpl;
import entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static service.GeneralConstants.*;


public class CheckoutService implements Service {

    private final BookDao bookDao = new BookDaoImpl();
    private final CartDao cartDao = new CartDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE);
        String languageCode = locale.substring(0, 2);
        List<Book> books = bookDao.getAll(languageCode);
        User user = (User) session.getAttribute(USER);
        session.setAttribute(BOOKS, books);
        Cart cart = cartDao.getCart(user.getId());
        Map<Book, Integer> cartItems = cart.getCartItems();

        long countOfInvalidQuantities = cartItems.keySet().stream()
                .filter(
                        cartItem -> books.stream()
                                .anyMatch(book -> book.getId() == cartItem.getId() &&
                                        cartItems.get(cartItem) > book.getQuantity())).count();

        if (countOfInvalidQuantities > 0) {
            HelperClass.getInstance().forwardToUriWithMessage(request, response, CART_URI, ERROR);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(CHECKOUT_URI);
            dispatcher.forward(request, response);
        }
    }
}

