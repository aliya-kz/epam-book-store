package service;

import entity.Cart;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static service.GeneralConstants.*;


public class WelcomeService implements Service {
    private static final HelperClass helperClass = HelperClass.getInstance();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ServletContext context = request.getSession().getServletContext();
        String locale = request.getParameter(LOCALE);
        session.setAttribute(LOCALE, locale);
        String lang = locale.substring(0, 2);
        helperClass.updateContextAttributes(context, lang);

        Cart cart = new Cart();
        session.setAttribute(CART, cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher(INDEX_URI);
        dispatcher.forward(request, response);
    }
}


