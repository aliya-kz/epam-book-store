package service;

import dao.*;

import dao.impl.*;
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
    private static final HelperClass helperClass = HelperClass.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);
        Map<Book, Integer> cartItems = cart.getCartItems();
        int cost = cart.getCost();
        User user = (User) session.getAttribute(USER);
        int addressId = Integer.parseInt(request.getParameter(ADDRESS));
        int cardId = Integer.parseInt(request.getParameter(CARD));
        Order order = new Order();
        user.getAddresses().stream()
                .filter(address -> address.getId() == addressId)
                .forEach(order::setAddress);
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        order.setOrderItems(cartItems);
        order.setUserId(user.getId());
        order.setDate(date);
        order.setStatusId(1);
        order.setCost(cost);
        order.setCardId(cardId);
        boolean quantitiesValidAndDecreased = bookDao.purchaseBooks(cartItems);
        if (quantitiesValidAndDecreased) {
            orderDao.addEntity(order);
            List<Order> orders = orderDao.getOrdersByUserId(user.getId());
            session.setAttribute(MY_ORDERS, orders);
            cartDao.deleteCart(user.getId());
            cart = new Cart();
            session.setAttribute(CART, cart);
            helperClass.updateBooksAttribute(session);
            RequestDispatcher dispatcher = request.getRequestDispatcher(PROFILE_ORDERS_URI);
            dispatcher.forward(request, response);
        } else {
            helperClass.updateBooksAttribute(session);
            helperClass.forwardToUriWithMessage(request, response, CART_URI, ERROR);
        }

    }
}
