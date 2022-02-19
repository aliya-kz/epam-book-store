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

public class CreateOrderService implements Service {

    private final CartDao cartDao = new CartDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final OrderDao orderDao = new OrderDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        int cost = cart.getCost();
        User user = (User) session.getAttribute("user");
        int addressId = Integer.parseInt(request.getParameter("address"));
        Order order = new Order();
        for (Address address: user.getAddresses()) {
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
        int errorBook = bookDao.purchaseBooks(cartItems);
        RequestDispatcher dispatcher;
        if (errorBook <= 0) {
            orderDao.addEntity(order);
            List<Order> orders = orderDao.getOrdersByUserId(user.getId());
            session.setAttribute("myOrders", orders);
            cartDao.deleteById(user.getId());
            cart = new Cart();
            session.setAttribute("cart", cart);
            dispatcher = request.getRequestDispatcher("/profile#prof-orders");
        } else {
            bookDao.returnBooks(cartItems, errorBook);
            dispatcher = request.getRequestDispatcher("/WEB-INF/view/cart.jsp?msg=error");
        }
        dispatcher.forward(request, response);
    }
}
