package service;

import DAO.CategoryDao;
import DAO.impl.CategoryDaoImpl;
import entity.Category;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddNewCategoryService implements Service {

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String title = request.getParameter("new_category");
        int id = Integer.parseInt(request.getParameter("new_id"));
        String lang = request.getParameter("cat_lang");
        Category category = new Category(id, title, lang);
        int result = categoryDao.addEntity(category);
        if (result == 0) {
            String uri = request.getParameter("uri");
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?msg=error");
            dispatcher.forward(request, response);
        } else {
            Service service = serviceFactory.getService("get_all_categories");
            service.execute(request, response);
        }
    }
}
