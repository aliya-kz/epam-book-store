package service;

import DAO.BaseDao;
import DAO.SqlDaoFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteEntityAdmin implements Service {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();

    private final SqlDaoFactory daoFactory = SqlDaoFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String table = request.getParameter("table");
        String lang = request.getParameter("lang");
        BaseDao dao = daoFactory.getDao(table);
        if (lang != null) {
            dao.deleteByIdLang(id, lang);
        } else {
            dao.deleteById(id);
        }
        String serviceName = "";
        if (lang == null) {
        serviceName = "get_all_" + table;
        } else {
        serviceName = "get_all_" + table.substring(0, table.indexOf("_"));
        }
        serviceFactory.getService(serviceName).execute(request, response);
        }
    }


