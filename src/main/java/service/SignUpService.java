package service;

import DAO.impl.CartDaoImpl;
import DAO.impl.UserDaoImpl;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SignUpService implements Service {
    private UserDaoImpl userDAO = new UserDaoImpl();

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
            String phone = request.getParameter("phone").replaceAll("[\\s\\-\\(\\)]","");
            String address = request.getParameter("address");
            String card = request.getParameter("card").replaceAll("[\\s\\-]","");
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
            int result = userDAO.addEntity(user);
            RequestDispatcher dispatcher;
            if (result == 0) {
                dispatcher = request.getRequestDispatcher("/WEB-INF/view/signUp.jsp?msg=error");
            } else {
                dispatcher = request.getRequestDispatcher("/WEB-INF/view/signUp.jsp?msg=success");
            }
            dispatcher.forward(request, response);
        }
    }
}
