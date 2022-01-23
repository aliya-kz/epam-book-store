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
import java.util.Locale;

public class GetAllAuthorsService implements Service {
    AuthorDao authorDao = SqlDaoFactory.getInstance().getAuthorDao();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute("locale");
        List<Author> authors = authorDao.getAll(locale);
        session.setAttribute("authors", authors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin-authors");
        dispatcher.forward(request, response);
    }
}
