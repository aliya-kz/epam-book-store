package service;

import DAO.impl.CartDaoImpl;
import entity.Book;
import entity.Cart;

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
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("id " + id);
        Cart cart = (Cart) session.getAttribute("cart");
        int qty = Integer.parseInt(request.getParameter("qty"));
        System.out.println("q " + qty);
        List<Book> books = (List<Book>) session.getAttribute("books");
        for (Book book: books) {
            if (book.getId() == id) {
                Map<Book, Integer> items = cart.getCartItems();
                items.put(book, qty);
                cart.setCartItems(items);
            }
        }
        System.out.println("book size " + cart.getCartItems().size());
        String uri = request.getParameter("uri");
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?msg=added");
        dispatcher.forward(request, response);
    }
}
