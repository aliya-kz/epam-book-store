package service;

import DAO.OrderDao;
import DAO.SqlDaoFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UpdateStatusService implements Service {

    OrderDao orderDao = SqlDaoFactory.getInstance().getOrderDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int orderId = Integer.parseInt(request.getParameter("order"));
        int statusId = Integer.parseInt(request.getParameter("status"));
        orderDao.updateStatus(orderId, statusId);
        Service service = new GetAllOrdersService();
        service.execute(request, response);
    }
}
