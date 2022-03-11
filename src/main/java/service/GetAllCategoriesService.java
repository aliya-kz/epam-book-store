package service;

import dao.impl.CategoryDaoImpl;
import entity.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static service.GeneralConstants.*;
import static service.GeneralConstants.ADMIN_CATEGORIES_URI;


public class GetAllCategoriesService implements Service {

    private final CategoryDaoImpl categoryDao = new CategoryDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE);
        if (locale == null) {
            locale = DEFAULT_LOCALE;
        }
        String lang = locale.substring(0, 2);
        List<Category> categories = categoryDao.getAll(lang);
        session.setAttribute(CATEGORIES, categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher(ADMIN_CATEGORIES_URI);
        dispatcher.forward(request, response);
    }
}
