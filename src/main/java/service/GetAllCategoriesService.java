package service;

import DAO.impl.CategoryDaoImpl;
import entity.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class GetAllCategoriesService implements Service{

    private final CategoryDaoImpl categoryDao = new CategoryDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute("locale");
        if (locale == null) {
            locale = "en_US";
        }
        String lang = locale.substring(0, 2);
        List<Category> categories = categoryDao.getAll(lang);
        session.setAttribute("categories", categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/adminCategories.jsp");
        dispatcher.forward(request, response);
    }
}
