package service;

import DAO.AuthorDao;
import DAO.impl.AuthorDaoImpl;
import entity.Author;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static service.GeneralConstants.*;


public class GetAllAuthorsService implements Service {

    private final AuthorDao authorDao = new AuthorDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE);
        if (locale == null) {
            locale = DEFAULT_LOCALE;
        }
        String languageCode = locale.substring(0, 2);
        List<Author> authors = authorDao.getAll(languageCode);
        session.setAttribute(AUTHORS, authors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin-authors");
        dispatcher.forward(request, response);
    }
}
