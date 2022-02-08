package service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteEntityAdmin implements Service {

    ServiceFactory serviceFactory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        String table = request.getParameter("table");
        String lang = request.getParameter("lang");
        String serviceName = "";
        if (lang == null) {
        serviceName = "get_all_" + table;
        } else {
        serviceName = "get_all_" + table.substring(0, table.indexOf("_"));
        }
        serviceFactory.getService(serviceName).execute(request, response);
        }
    }


