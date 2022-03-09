package service;

import DAO.BookDao;
import DAO.CartDao;
import DAO.impl.BookDaoImpl;
import DAO.impl.CartDaoImpl;
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
        RequestDispatcher dispatcher;
        int count = 0;
        for (Book book : cartItems.keySet()) {
            for (Book listBook : books) {
                if (book.getId() == listBook.getId()) {
                    book = listBook;
                    if (cartItems.get(book) > book.getQuantity()) {
                        count++;
                        cartItems.replace(book, book.getQuantity());
                    }
                }
            }
        }

        if (count > 0) {
            dispatcher = request.getRequestDispatcher("/WEB-INF/view/cart.jsp?" + MESSAGE + "=" + ERROR);
            dispatcher.forward(request, response);
        } else {
            dispatcher = request.getRequestDispatcher("/WEB-INF/view/checkout.jsp");
            dispatcher.forward(request, response);
        }
    }
}
