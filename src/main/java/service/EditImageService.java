package service;

import DAO.AuthorDao;
import DAO.BookDao;
import DAO.SqlDaoFactory;
import entity.Author;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class EditImageService implements Service {
    private final AuthorDao authorDao = SqlDaoFactory.getInstance().getAuthorDao();
    private final BookDao bookDao = SqlDaoFactory.getInstance().getBookDao();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String lang = request.getParameter("lang");
        String uri = request.getParameter("uri");
        int id = Integer.parseInt(request.getParameter("id"));
        String table = request.getParameter("table");
        HttpSession session = request.getSession();
        Part part = null;
        try {
            part = request.getPart("file");
        } catch (ServletException e) {
            e.printStackTrace();
        }
        InputStream is = ((Part) part).getInputStream();
        byte[] bytes = is.readAllBytes();
        int result = 0;
        if (table.equals("authors")) {
            result = authorDao.setColumnValue(table, id, "image", bytes);
            ServiceFactory.getInstance().getService("get_all_authors").execute(request, response);
        } else if (table.equals("book_covers")) {
            result = bookDao.setColumnValue(table, id, "image", bytes);
        }
    }
}

