package service;

import dao.BookDao;
import dao.impl.BookDaoImpl;
import dao.impl.CartDaoImpl;
import entity.Book;
import entity.Cart;
import entity.User;

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
    private static final HelperClass helperClass = HelperClass.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        long bookId = Integer.parseInt(request.getParameter(ID));
        Cart cart = (Cart) session.getAttribute(CART);
        if (cart == null) {
            cart = new Cart();
        }
        Map<Book, Integer> cartItems = cart.getCartItems();
        int quantity = Integer.parseInt(request.getParameter(QUANTITY));
        String locale = (String) session.getAttribute(LOCALE);
        String languageCode = locale.substring(0, 2);
        List<Book> books = bookDao.getAll(languageCode);
        User user = (User) session.getAttribute(USER);
        books.stream()
                .filter(book -> book.getId() == bookId)
                .forEach(book -> {
                    if (cartItems.containsKey(book)) {
                        if (cartItems.get(book) + quantity <= book.getQuantity()) {
                            cartItems.replace(book, cartItems.get(book), Math.min(cartItems.get(book) + quantity, MAX_QUANTITY_CART_ITEM));
                        } else {
                            cartItems.replace(book, cartItems.get(book), Math.min(book.getQuantity(), MAX_QUANTITY_CART_ITEM));
                        }
                    } else {
                        cartItems.put(book, quantity);
                    }
                });
        if (user != null) {
            cartDao.addToCart(user.getId(), bookId, quantity);
            helperClass.updateCartAttribute(session, user.getId());
            helperClass.updateUserAttribute(session, user.getId());
        }
        String uri = request.getParameter(URI);
        helperClass.forwardToUriWithMessage(request, response, uri, ADDED);
    }
}
