package service;

import DAO.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.GeneralConstants.*;


public class BlockUserService implements Service {

    private final UserDaoImpl userDAO = new UserDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter(USER_ID));
        boolean status = Boolean.parseBoolean(request.getParameter(BLOCKED_STATUS));
        userDAO.blockUser(id, status);
        GetAllUsersService getUsers = new GetAllUsersService();
        getUsers.execute(request, response);
    }
}


