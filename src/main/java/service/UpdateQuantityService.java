package service;

import dao.CartDao;
import dao.impl.CartDaoImpl;
import entity.Book;
import entity.Cart;
import entity.User;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static service.GeneralConstants.*;


public class UpdateQuantityService implements Service {

    private final CartDao cartDao = new CartDaoImpl();
    private static final HelperClass helperClass = HelperClass.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        int bookId = Integer.parseInt(request.getParameter(ID));
        int quantity = Integer.parseInt(request.getParameter(QUANTITY));
        String uri = request.getParameter(URI);
        User user = (User) session.getAttribute(USER);
        Cart cart;
        if (user == null) {
            cart = (Cart) session.getAttribute(CART);
            Map<Book, Integer> items = cart.getCartItems();
            Book book = new Book(bookId);
            int oldQuantity = items.get(book);
            items.replace(book, oldQuantity, quantity);
        } else {
            long userId = user.getId();
            cartDao.updateQuantity(bookId, userId, quantity);
            cart = cartDao.getCart(userId);
            helperClass.updateBooksAttribute(session);
        }
        session.setAttribute(CART, cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
        dispatcher.forward(request, response);
    }
}
