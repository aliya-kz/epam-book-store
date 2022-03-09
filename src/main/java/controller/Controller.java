package controller;

import service.Service;
import service.ServiceFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private final ServiceFactory factory = ServiceFactory.getInstance();

    public static final long serialVersionUID = 1L;

    public Controller() {
       super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceName = request.getParameter("service_name");
        Service service = factory.getService(serviceName);
        service.execute(request, response);
    }
}
