package service;

import dao.AuthorDao;
import dao.impl.AuthorDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.GeneralConstants.*;
import static service.ServiceConstants.*;

public class EditAuthorService implements Service {

    private final static ServiceFactory factory = ServiceFactory.getInstance();
    private final AuthorDao authorDao = new AuthorDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter(ID));
        String lang = request.getParameter(LANGUAGE);
        String name = request.getParameter(NEW_NAME);
        if (name != null && name.length() > 0) {
            authorDao.setColumnValueLang(AUTHORS_LANG, id, NAME, name, lang);
        }
        String surname = request.getParameter(NEW_SURNAME);
        if (surname != null && surname.length() > 0) {
            authorDao.setColumnValueLang(AUTHORS_LANG, id, SURNAME, surname, lang);
        }
        String biography = request.getParameter(NEW_BIOGRAPHY);
        if (biography != null && biography.length() > 0) {
            authorDao.setColumnValueLang(AUTHORS_LANG, id, BIOGRAPHY, biography, lang);
        }
        factory.getService(GET_ALL_AUTHORS_SERVICE).execute(request, response);
    }
}
