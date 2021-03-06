package service;

import dao.AuthorDao;
import dao.impl.AuthorDaoImpl;
import entity.Author;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static service.GeneralConstants.*;
import static service.ServiceConstants.GET_ALL_AUTHORS_SERVICE;

public class AddAuthorTranslationService implements Service {

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final AuthorDao authorDao = new AuthorDaoImpl();
    private static final HelperClass helperClass = HelperClass.getInstance();

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
            helperClass.forwardToUriWithMessage(request, response, ADMIN_AUTHORS_URI, AUTHOR_EXISTS);
        } else {
            boolean entityAdded = authorDao.addTranslation(author);
            if (entityAdded) {
                Service getAllAuthorsService = serviceFactory.getService(GET_ALL_AUTHORS_SERVICE);
                getAllAuthorsService.execute(request, response);
            } else {
                helperClass.forwardToUriWithMessage(request, response, ADMIN_AUTHORS_URI, AUTHOR_NOT_EXISTS);
            }
        }
    }
}
