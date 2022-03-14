package service;

import dao.AuthorDao;
import dao.impl.AuthorDaoImpl;
import entity.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static service.GeneralConstants.*;
import static service.ServiceConstants.GET_ALL_AUTHORS_SERVICE;

public class AddAuthorTranslationService implements Service {

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static final AuthorDao authorDao = new AuthorDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String biography = request.getParameter(BIOGRAPHY);
        String lang = request.getParameter(LANGUAGE);
        Author author = new Author(name, surname, biography, lang);
        String idString = request.getParameter(ID);
        int id = Integer.parseInt(idString);
        author.setId(id);
        boolean entityAlreadyExists = authorDao.authorWithIdAndLangExists(author);
        if (entityAlreadyExists) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(ADMIN_AUTHORS_URI + "?" + MESSAGE + "="
                    + AUTHOR_EXISTS);
            dispatcher.forward(request, response);
        } else {
            boolean entityAdded = authorDao.addTranslation(author);
            if (entityAdded) {
                Service getAllAuthorsService = serviceFactory.getService(GET_ALL_AUTHORS_SERVICE);
                getAllAuthorsService.execute(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher(ADMIN_AUTHORS_URI + "?"
                        + MESSAGE + "=" + AUTHOR_NOT_EXISTS);
                dispatcher.forward(request, response);
            }
        }
    }
}
