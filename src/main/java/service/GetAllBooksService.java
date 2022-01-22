package service;

import DAO.impl.BookDaoImpl;
import entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class GetAllBooksService implements Service {
    BookDaoImpl bookDao = new BookDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Book> books = bookDao.getAll();
        HttpSession session = request.getSession();
        session.setAttribute("books", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/adminBooks.jsp");
        dispatcher.forward(request, response);
    }
}
