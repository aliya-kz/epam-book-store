package service;

import DAO.AuthorDao;
import DAO.impl.AuthorDaoImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static service.GeneralConstants.*;

public class SearchService implements Service {

    private static final AuthorDao authorDao = new AuthorDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String searchInput = request.getParameter(SEARCH).trim();
        List<Integer> foundAuthors = authorDao.searchAuthors(searchInput);
        request.setAttribute(FOUND, foundAuthors);
        String uri = request.getParameter(URI);
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
        dispatcher.forward(request, response);
    }
}
