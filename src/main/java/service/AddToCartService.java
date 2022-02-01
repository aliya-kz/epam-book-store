package service;

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
    CartDaoImpl cartDao = new CartDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int bookId = Integer.parseInt(request.getParameter("id"));
        Cart cart = (Cart) session.getAttribute("cart");
        Map<Book, Integer> items = cart.getCartItems();
        int qty = Integer.parseInt(request.getParameter("qty"));
        List<Book> books = (List<Book>) session.getAttribute("books");
        User user = (User) session.getAttribute("user");
        for (Book book : books) {
            if (book.getId() == bookId) {
                if (items.containsKey(book)) {
                    int oldQty = items.get(book);
                    oldQty = items.replace(book, oldQty + qty);
                } else {
                    items.put(book, qty);
                }
            }
        }

        for (Book book: items.keySet()) {
            System.out.println(book.getTitle());
        }
        if (user != null) {
            cartDao.addToCart(user.getId(), bookId, qty);
        }
        String uri = request.getParameter("uri");
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?msg=added");
        dispatcher.forward(request, response);
    }
}
