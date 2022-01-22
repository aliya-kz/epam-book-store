package service;

import DAO.CategoryDao;
import DAO.impl.CategoryDaoImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditCategoryService implements Service{
    private static ServiceFactory factory = ServiceFactory.getInstance();
    CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String value = request.getParameter("new_value");
        String lang = request.getParameter("lang");
        int id = Integer.parseInt(request.getParameter("category_id"));
        categoryDao.setColumnValueLang("categories_lang", id, "category_name", value, lang);
        Service service = factory.getService("get_all_categories");
        service.execute(request, response);
    }
}
