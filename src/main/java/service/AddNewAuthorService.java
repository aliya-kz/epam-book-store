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

public class AddNewAuthorService implements Service {

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static final AuthorDao authorDao = new AuthorDaoImpl();
    private static final HelperClass helperClass = HelperClass.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter(NAME);
        String surname = request.getParameter(SURNAME);
        String biography = request.getParameter(BIOGRAPHY);
        String lang = request.getParameter(LANGUAGE);
        Author author = new Author(name, surname, biography, lang);
        boolean entityAdded;
        byte[] bytes = helperClass.partToBytes(request, FILE);
        author.setImage(bytes);
        entityAdded = authorDao.addEntity(author);

        if (entityAdded) {
            Service getAllAuthorsService = serviceFactory.getService(GET_ALL_AUTHORS_SERVICE);
            getAllAuthorsService.execute(request, response);
        } else {
              helperClass.forwardToUriWithMessage(request, response,ADMIN_AUTHORS_URI,ERROR);
        }
    }
}
