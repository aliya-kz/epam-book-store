package controller;

import service.Service;
import service.ServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.ServiceConstants.*;


public class ImageServlet extends HttpServlet {

    private final ServiceFactory factory = ServiceFactory.getInstance();
    public static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Service service = factory.getService(GET_IMAGE_SERVICE);
        service.execute(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceName = request.getParameter(SERVICE_NAME);
        Service service = factory.getService(serviceName);
        service.execute(request, response);
    }
}