package service;

import DAO.CardDao;
import DAO.SqlDaoFactory;
import DAO.UserDao;
import entity.Card;
import entity.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddCardService implements Service{
    private static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CardDao cardDao = SqlDaoFactory.getInstance().getCardDao();
    UserDao userDao = SqlDaoFactory.getInstance().getUserDao();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String cardNumber = request.getParameter("card");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        Card card = new Card(cardNumber);
        card.setUserId(userId);
        String uri = request.getParameter("uri");
        int result = cardDao.addEntity(card);
        RequestDispatcher dispatcher;
        user = userDao.getUser(userId);
        session.setAttribute("user", user);
        if (result < 1) {
            dispatcher = request.getRequestDispatcher(uri + "?msg=error");
        } else {
            dispatcher = request.getRequestDispatcher(uri);
        }
        dispatcher.forward(request, response);
    }
}