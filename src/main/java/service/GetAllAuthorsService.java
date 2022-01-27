package service;

import DAO.AuthorDao;
import DAO.SqlDaoFactory;
import entity.Author;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class GetAllAuthorsService implements Service {
    AuthorDao authorDao = SqlDaoFactory.getInstance().getAuthorDao();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute("locale");
        if (locale == null) {
            locale = "en_US";
        }
        String lang = locale.substring(0, 2);
        List<Author> authors = authorDao.getAll(lang);
        session.setAttribute("authors", authors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin-authors");
        dispatcher.forward(request, response);
    }
}
