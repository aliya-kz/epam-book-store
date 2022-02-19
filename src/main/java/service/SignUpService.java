package service;

import DAO.CartDao;
import DAO.UserDao;
import DAO.impl.CartDaoImpl;
import DAO.impl.UserDaoImpl;
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

public class SignUpService implements Service {

    private final UserDao userDao = new UserDaoImpl();
    private final  CartDao cartDao = new CartDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        boolean userExists = userDao.userExists(email);
        if (userExists) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/signup?msg=user_exists");
            dispatcher.forward(request, response);
        } else {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            Date dateOfBirth = Date.valueOf(request.getParameter("date_of_birth"));
            String phone = request.getParameter("phone").replaceAll("[\\s\\-\\(\\)]","");
            Address address = new Address();
            address.setAddress(request.getParameter("address"));
            String card = request.getParameter("card").replaceAll("[\\s\\-]","");
            String password = request.getParameter("password");
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
            List <Card> cards = new ArrayList<>();
            cards.add(new Card(card));
            user.setCards(cards);
            user.setPassword(password);
            int userId = userDao.addEntity(user);
            RequestDispatcher dispatcher;
            if (userId < 1) {
                dispatcher = request.getRequestDispatcher("/WEB-INF/view/signUp.jsp?msg=error");
            } else {
                Cart cart = (Cart) session.getAttribute("cart");
                cart.setUserId(userId);
                cartDao.addEntity(cart);
                dispatcher = request.getRequestDispatcher("/WEB-INF/view/signUp.jsp?msg=success");
            }
            dispatcher.forward(request, response);
        }
    }
}
