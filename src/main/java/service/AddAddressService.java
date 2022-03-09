package service;

import DAO.AddressDao;
import DAO.UserDao;
import DAO.impl.AddressDaoImpl;
import DAO.impl.UserDaoImpl;
import entity.Address;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static service.GeneralConstants.*;


public class AddAddressService implements Service {

    private static final UserDao userDao = new UserDaoImpl();
    private static final AddressDao addressDao = new AddressDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String addressString = request.getParameter(ADDRESS);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long userId = user.getId();
        Address address = new Address();
        address.setAddress(addressString);
        address.setUserId(userId);
        int result = addressDao.addEntity(address);
        String uri = request.getParameter(URI);
        RequestDispatcher dispatcher;
        if (result == 0) {
            dispatcher = request.getRequestDispatcher(uri + "?" + MESSAGE + "=" + ERROR);
        } else {
            user = userDao.getUser(userId);
            session.setAttribute(USER, user);
            dispatcher = request.getRequestDispatcher(uri);
        }
        dispatcher.forward(request, response);
    }
}
