package service;

import dao.impl.UserDaoImpl;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import static service.GeneralConstants.*;


public class GetAllUsersService implements Service {

    private final UserDaoImpl userDao = new UserDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<User> users = userDao.getAll();
        HttpSession session = request.getSession();
        session.setAttribute(USER, users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/adminUsers.jsp");
        dispatcher.forward(request, response);
    }
}
