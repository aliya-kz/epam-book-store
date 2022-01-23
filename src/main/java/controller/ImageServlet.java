package controller;

import DAO.BaseDao;
import DAO.impl.BookDaoImpl;
import service.Service;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ImageServlet extends HttpServlet {
    ServiceFactory factory = ServiceFactory.getInstance();
    public static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int imageId = Integer.parseInt(request.getParameter("image_id"));
        String table = request.getParameter("table");
        BaseDao dao = new BookDaoImpl();
        byte[] imageBytes = dao.getByteImage(imageId, table);
        response.setContentType("image/jpeg");
        response.setContentLength(imageBytes.length);
        response.getOutputStream().write(imageBytes);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceName = request.getParameter("service_name");
        Service service = factory.getService(serviceName);
        service.execute(request, response);
    }
}