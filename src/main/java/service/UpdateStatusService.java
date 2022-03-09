package service;

import DAO.OrderDao;
import DAO.impl.OrderDaoImpl;
import entity.Order;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static service.GeneralConstants.*;


public class UpdateStatusService implements Service {

    private final OrderDao orderDao = new OrderDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int orderId = Integer.parseInt(request.getParameter(ORDER));
        int statusId = Integer.parseInt(request.getParameter(STATUS));
        orderDao.updateStatus(orderId, statusId);
        User user = (User) session.getAttribute(USER);
        if (user == null) {
            Service service = new GetAllOrdersService();
            service.execute(request, response);
        } else {
            List<Order> myOrders = orderDao.getOrdersByUserId(user.getId());
            session.setAttribute(MY_ORDERS, myOrders);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/profile#prof-orders");
            dispatcher.forward(request, response);
        }
    }
}
