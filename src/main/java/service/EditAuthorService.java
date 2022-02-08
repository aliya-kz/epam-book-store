package service;

import DAO.AuthorDao;
import DAO.impl.AuthorDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditAuthorService implements Service {
    private static ServiceFactory factory = ServiceFactory.getInstance();
    AuthorDao authorDao = new AuthorDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String lang = request.getParameter("lang");
        String name = request.getParameter("new_name");
        if (name != null && name.length()>0) {
            authorDao.setColumnValueLang("authors_lang", id, "name", name, lang);
        }
        String surname = request.getParameter("new_surname");
        if (surname != null && surname.length()>0) {
            authorDao.setColumnValueLang("authors_lang", id, "surname", surname, lang);
        }
        String biography  = request.getParameter("new_biography");
        if (biography != null && biography.length()>0) {
            authorDao.setColumnValueLang("authors_lang", id, "biography", biography, lang);
        }
        factory.getService("get_all_authors").execute(request, response);
    }
}
