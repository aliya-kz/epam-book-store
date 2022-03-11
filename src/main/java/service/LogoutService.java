package service;

import entity.Cart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static service.GeneralConstants.*;


public class LogoutService implements Service {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        deleteUserAttributes(session);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
        requestDispatcher.forward(request, response);
    }

    public void deleteUserAttributes (HttpSession session) {
        session.removeAttribute(MY_ORDERS);
        session.removeAttribute(USER);
        Cart cart = new Cart();
        session.setAttribute(CART, cart);
    }
}
