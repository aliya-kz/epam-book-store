package service;

import DAO.BookDao;
import DAO.CartDao;
import DAO.OrderDao;
import DAO.impl.BookDaoImpl;
import DAO.impl.CartDaoImpl;
import DAO.impl.OrderDaoImpl;
import entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static service.GeneralConstants.*;


public class CreateOrderService implements Service {

    private final CartDao cartDao = new CartDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final OrderDao orderDao = new OrderDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        int cost = cart.getCost();
        User user = (User) session.getAttribute(USER);
        int addressId = Integer.parseInt(request.getParameter(ADDRESS));
        Order order = new Order();
        for (Address address : user.getAddresses()) {
            if (addressId == address.getId()) {
                order.setAddress(address);
            }
        }

        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        order.setOrderItems(cart.getCartItems());
        order.setUserId(user.getId());
        order.setDate(date);
        order.setStatusId(1);
        order.setCost(cost);
        Map<Book, Integer> cartItems = cart.getCartItems();
        long errorBook = bookDao.purchaseBooks(cartItems);
        RequestDispatcher dispatcher;
        if (errorBook <= 0) {
            orderDao.addEntity(order);
            List<Order> orders = orderDao.getOrdersByUserId(user.getId());
            session.setAttribute(MY_ORDERS, orders);
            cartDao.deleteById(user.getId());
            cart = new Cart();
            session.setAttribute(CART, cart);
            String locale = (String) session.getAttribute(LOCALE);
            String languageCode = locale.substring(0, 2);
            List<Book> books = bookDao.getAll(languageCode);
            session.setAttribute(BOOKS, books);
            dispatcher = request.getRequestDispatcher("/profile#prof-orders");
        } else {
            bookDao.returnBooks(cartItems, errorBook);
            dispatcher = request.getRequestDispatcher("/WEB-INF/view/cart.jsp?" + MESSAGE + "=" + ERROR);
        }
        dispatcher.forward(request, response);
    }
}
