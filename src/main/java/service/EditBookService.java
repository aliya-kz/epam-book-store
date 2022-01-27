package service;

import DAO.BookDao;
import DAO.impl.BookDaoImpl;
import entity.Book;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class EditBookService implements Service{

    BookDao bookDao = new BookDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String newValue = request.getParameter("new_value");
        String column = request.getParameter("column");
        String uri = (String) request.getAttribute("uri");
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        int result = bookDao.setColumnValue("books", id, column, newValue);
        if (result < 1) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri+"?msg=error");
            dispatcher.forward(request, response);
        }
        else {
            List<Book> books = bookDao.getAll((String)session.getAttribute("locale"));
            session.setAttribute("books", books);
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
        }

    }
}
