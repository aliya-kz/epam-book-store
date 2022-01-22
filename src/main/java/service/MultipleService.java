package service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class MultipleService implements Service {
    // init dao
    //UserDao userDao = new UserDao();
   // BookDao bookDao = new BookDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       /* EntityTransaction transaction = new EntityTransaction();
        transaction.begin (userDao, bookDao);
        try {
            userDao.create();
            bookDao.update();
        } catch (SQLException e) {
            transaction.rollback();
        } finally {
            transaction.end();
        }*/
    }
}
