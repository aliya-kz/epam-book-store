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
    private static final UserDao userDao = new UserDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String cardNumber = request.getParameter(PROF_CARD);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long userId = user.getId();
        Card card = new Card(cardNumber);
        card.setUserId(userId);
        String uri = request.getParameter(URI);
        int result = cardDao.addEntity(card);
        RequestDispatcher dispatcher;
        user = userDao.getUser(userId);
        session.setAttribute(USER, user);
        if (result < 1) {
            dispatcher = request.getRequestDispatcher(uri + "?" + MESSAGE + "=" + ERROR);
        } else {
            dispatcher = request.getRequestDispatcher(uri);
        }
        dispatcher.forward(request, response);
    }
}