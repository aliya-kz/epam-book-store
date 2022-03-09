package service;

import DAO.AuthorDao;

import DAO.impl.AuthorDaoImpl;
import entity.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static service.GeneralConstants.*;
import static service.ServiceConstants.GET_ALL_AUTHORS_SERVICE;

public class AddNewAuthorService implements Service {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
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
        String uri = request.getParameter(URI);
        int result = 0;
        if (idString == null) {
            Part part = null;
            try {
                part = request.getPart(FILE);
            } catch (ServletException e) {
                LOGGER.info(e);
                e.printStackTrace();
            }
            InputStream is = ((Part) part).getInputStream();
            byte[] bytes = is.readAllBytes();
            author.setImage(bytes);
            result = authorDao.addEntity(author);
        } else {
            int id = Integer.parseInt(idString);
            author.setId(id);
            result = authorDao.addTranslation(author);
        }
        if (result > 0) {
            Service getAllAuthorsService = serviceFactory.getService(GET_ALL_AUTHORS_SERVICE);
            getAllAuthorsService.execute(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?" + MESSAGE + "=" + ERROR);
            dispatcher.forward(request, response);
        }
    }
}
