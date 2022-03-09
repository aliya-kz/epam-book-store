package service;

import dao.CartDao;
import dao.UserDao;
import dao.impl.CartDaoImpl;
import dao.impl.UserDaoImpl;
import entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static service.GeneralConstants.*;

public class SignUpService implements Service {

    private final UserDao userDao = new UserDaoImpl();
    private final CartDao cartDao = new CartDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter(EMAIL);
        HttpSession session = request.getSession();
        boolean userExists = userDao.userExists(email);
        if (userExists) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup?" + MESSAGE + "=" + USER_EXISTS);
            dispatcher.forward(request, response);
        } else {
            String name = request.getParameter(NAME);
            String surname = request.getParameter(SURNAME);
            Date dateOfBirth = Date.valueOf(request.getParameter(DATE_OF_BIRTH));
            String phone = request.getParameter(PHONE).replaceAll("[\\s\\-\\(\\)]", "");
            Address address = new Address();
            address.setAddress(request.getParameter(ADDRESS));
            String card = request.getParameter(CARD).replaceAll("[\\s\\-]", "");
            String password = request.getParameter(PASSWORD);
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setDateOfBirth(dateOfBirth);
            user.setEmail(email);
            user.setPhone(phone);
            List<Address> addresses = new ArrayList<>();
            addresses.add(address);
            user.setAddresses(addresses);
            user.setAdmin(false);
            List<Card> cards = new ArrayList<>();
            cards.add(new Card(card));
            user.setCards(cards);
            user.setPassword(password);
            int userId = userDao.addEntity(user);
            RequestDispatcher dispatcher;
            if (userId < 1) {
                dispatcher = request.getRequestDispatcher("/WEB-INF/view/signUp.jsp?" + MESSAGE + "=" + ERROR);
            } else {
                Cart cart = (Cart) session.getAttribute(CART);
                cart.setUserId(userId);
                cartDao.addEntity(cart);
                dispatcher = request.getRequestDispatcher("/WEB-INF/view/signUp.jsp?" + MESSAGE + "=" + SUCCESS);
            }
            dispatcher.forward(request, response);
        }
    }
}
