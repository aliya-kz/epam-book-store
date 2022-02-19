package service;


import DAO.CartDao;
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
import java.util.Map;


public class UpdateQuantityService implements Service {

    CartDao cartDao = new CartDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int bookId = Integer.parseInt(request.getParameter("id"));
        int qty = Integer.parseInt(request.getParameter("qty"));
        String uri = request.getParameter("uri");
        User user = (User) session.getAttribute("user");
        Cart cart;
        if (user == null) {
            cart = (Cart) session.getAttribute("cart");
            Map<Book, Integer> items = cart.getCartItems();
            Book book = new Book (bookId);
            int oldQty = items.get(book);
            items.replace(book, oldQty, qty);
        } else {
            int userId = user.getId();
            int result = cartDao.updateQuantity(bookId, userId, qty);
            cart = cartDao.getCart(userId);

        }
        session.setAttribute("cart", cart);
        RequestDispatcher dispatcher  = request.getRequestDispatcher(uri);
        dispatcher.forward(request, response);
    }
}
