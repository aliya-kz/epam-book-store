package service;

import DAO.AuthorDao;
import DAO.impl.AuthorDaoImpl;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class SearchService implements Service {

    private static final AuthorDao authorDao = new AuthorDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String searchInput = request.getParameter("search").trim();
        String table = request.getParameter("table");
        List <Integer> foundAuthors = authorDao.searchAuthors(searchInput);
        System.out.println("found " + foundAuthors.size());
        request.setAttribute("found", foundAuthors);
        String uri = request.getParameter("uri");
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
        dispatcher.forward(request, response);
    }
}