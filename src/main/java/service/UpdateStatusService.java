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


public class UpdateStatusService implements Service {

    OrderDao orderDao = new OrderDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int orderId = Integer.parseInt(request.getParameter("order"));
        int statusId = Integer.parseInt(request.getParameter("status"));
        orderDao.updateStatus(orderId, statusId);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            Service service = new GetAllOrdersService();
            service.execute(request, response);
        } else {
            List<Order> myOrders = orderDao.getOrdersByUserId(user.getId());
            session.setAttribute("myOrders", myOrders);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/profile#prof-orders");
            dispatcher.forward(request, response);
        }
    }
}
