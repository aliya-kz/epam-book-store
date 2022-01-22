package service;

import DAO.AuthorDao;

import DAO.SqlDaoFactory;

import entity.Author;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;


public class AddNewAuthorService implements Service {
    private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final AuthorDao authorDao = SqlDaoFactory.getInstance().getAuthorDao();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String biography = request.getParameter("biography");
        String lang = request.getParameter("lang");
        Author author = new Author(name, surname, biography, lang);
        String idString = request.getParameter("id");
        String uri = request.getParameter("uri");
        System.out.println("uri "+ uri);
        int result = 0;
        if (idString == null) {
            Part part = null;
            try {
                part = request.getPart("file");
            } catch (ServletException e) {
                e.printStackTrace();
            }
            InputStream is = ((Part) part).getInputStream();
            byte[] bytes = is.readAllBytes();
            author.setImage(bytes);
            result = authorDao.addEntity(author);
            System.out.println("result" + result);
        } else {
            int id = Integer.parseInt(idString);
            author.setId(id);
            result = authorDao.addTranslation(author);
        }
        if (result > 0) {
            Service getAllAuthorsService = serviceFactory.getService("get_all_authors");
            getAllAuthorsService.execute(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?msg=error");
            dispatcher.forward(request, response);
        }
    }
}
