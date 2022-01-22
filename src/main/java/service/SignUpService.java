package service;

import DAO.impl.CartDaoImpl;
import DAO.impl.UserDaoImpl;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SignUpService implements Service {
    private UserDaoImpl userDAO = new UserDaoImpl();
    private CartDaoImpl cartDao = new CartDaoImpl();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        boolean userExists = userDAO.userExists(email);
        if (userExists) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup?msg=user_exists");
            dispatcher.forward(request, response);
        } else {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            Date dateOfBirth = Date.valueOf(request.getParameter("date_of_birth"));
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String card = request.getParameter("card");
            String password = request.getParameter("password");
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setDateOfBirth(dateOfBirth);
            user.setEmail(email);
            user.setPhone(phone);
            List<String> addresses = new ArrayList<>();
            addresses.add(address);
            user.setAddresses(addresses);
            List <String> cards = new ArrayList<>();
            cards.add(card);
            user.setCards(cards);
            user.setPassword(password);
            userDAO.addEntity(user);
            //cartDao.createCart(email);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup?msg=success");
            dispatcher.forward(request, response);
        }
    }
}
