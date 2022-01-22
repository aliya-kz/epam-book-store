package service;

import DAO.CategoryDao;
import DAO.impl.CategoryDaoImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteEntityService implements Service{
    CategoryDao dao = new CategoryDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String table = request.getParameter("table");
        String lang = request.getParameter("lang");
        System.out.println(lang);
        if (lang == null) {
            dao.deleteById(table, id);
        } else {
            dao.deleteByIdLang(table, id, lang);
        }
        Service service = new GetAllCategoriesService();
        service.execute(request, response);
    }
}
