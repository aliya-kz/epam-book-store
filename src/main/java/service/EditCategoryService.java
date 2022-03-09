package service;

import dao.CategoryDao;
import dao.impl.CategoryDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.GeneralConstants.*;
import static service.ServiceConstants.*;

public class EditCategoryService implements Service {

    private final static ServiceFactory factory = ServiceFactory.getInstance();
    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String value = request.getParameter(NEW_VALUE);
        String lang = request.getParameter(LANGUAGE);
        int id = Integer.parseInt(request.getParameter(CATEGORY_ID));
        categoryDao.setColumnValueLang(CATEGORIES_LANG, id, CATEGORY_NAME, value, lang);
        Service service = factory.getService(GET_ALL_CATEGORIES_SERVICE);
        service.execute(request, response);
    }
}
