package service;

import dao.impl.OrderDaoImpl;
import entity.Order;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static service.GeneralConstants.*;


public class GetAllOrdersService implements Service {

    private final OrderDaoImpl orderDao = new OrderDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Order> orders = orderDao.getAll();
        HttpSession session = request.getSession();
        session.setAttribute(ORDERS, orders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/adminOrders.jsp");
        dispatcher.forward(request, response);
    }
}
