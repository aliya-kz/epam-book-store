package service;

import dao.CategoryDao;
import dao.impl.CategoryDaoImpl;
import entity.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.GeneralConstants.*;
import static service.ServiceConstants.*;

public class AddNewCategoryService implements Service {

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static final CategoryDao categoryDao = new CategoryDaoImpl();
    private static final HelperClass helperClass = HelperClass.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String title = request.getParameter(NEW_VALUE);
        int id = Integer.parseInt(request.getParameter(NEW_ID));
        String lang = request.getParameter(CATEGORY_LANGUAGE);
        Category category = new Category(id, title, lang);
        boolean entityAdded = categoryDao.addEntity(category);
        if (!entityAdded) {
            helperClass.forwardToUriWithMessage(request, response, ADMIN_CATEGORIES_URI, ERROR);
        } else {
            Service service = serviceFactory.getService(GET_ALL_CATEGORIES_SERVICE);
            service.execute(request, response);
        }
    }
}
