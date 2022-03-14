package service;

import dao.CardDao;
import dao.UserDao;
import dao.impl.CardDaoImpl;
import dao.impl.UserDaoImpl;
import entity.Card;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static service.GeneralConstants.*;


public class AddCardService implements Service {

    private final CardDao cardDao = new CardDaoImpl();
    private static HelperClass helperClass = HelperClass.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String cardNumber = request.getParameter(PROFILE_CARD);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long userId = user.getId();
        Card card = new Card(cardNumber);
        card.setUserId(userId);
        String uri = request.getParameter(URI);
        boolean entityAdded = cardDao.addEntity(card);
        helperClass.updateUserAttribute(session, userId);
        if (!entityAdded) {
            helperClass.forwardToUriWithMessage(request, response, uri, ERROR);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
        }
    }
}