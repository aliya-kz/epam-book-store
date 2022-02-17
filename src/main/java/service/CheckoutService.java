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

public class CheckoutService implements Service {

    BookDao bookDao = new BookDaoImpl();
    CartDao cartDao = new CartDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute("locale");
        List<Book> books = bookDao.getAll(locale.substring(0, 2));
        User user = (User) session.getAttribute("user");
        session.setAttribute("books", books);
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
            dispatcher = request.getRequestDispatcher("/WEB-INF/view/cart.jsp?msg=error");
            dispatcher.forward(request, response);
        } else {
            dispatcher = request.getRequestDispatcher("/WEB-INF/view/checkout.jsp");
            dispatcher.forward(request, response);
        }
    }
}
