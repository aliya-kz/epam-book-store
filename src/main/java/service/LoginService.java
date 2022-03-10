package service;

import dao.*;
import dao.impl.*;
import entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static service.GeneralConstants.*;


public class LoginService implements Service {

    private final UserDao userDao = new UserDaoImpl();
    private final CartDao cartDao = new CartDaoImpl();
    private final WishListDao wishListDao = new WishListDaoImpl();
    private final OrderDao orderDao = new OrderDaoImpl();
    private final StatusDao statusDao = new StatusDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();
        String locale = (String) session.getAttribute(LOCALE);
        String lang = locale.substring(0, 2);
        boolean userIsValid= userDao.validateUser(email, password);
        if (!userIsValid) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/logIn.jsp?" + MESSAGE + "=" + WRONG);
            dispatcher.forward(request, response);
        } else {
            long userId = userDao.getIdByEmail(email);
            User user = userDao.getUser(userId);
            if (user.isBlocked()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/logIn.jsp?" + MESSAGE + "=" + BLOCKED);
                dispatcher.forward(request, response);
            } else if (user.isAdmin()) {
                List<Status> statuses = statusDao.getAll(lang);
                session.setAttribute(STATUSES, statuses);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/adminBooks.jsp");
                dispatcher.forward(request, response);
            } else {
                session.setAttribute(USER, user);
                Cart cart = cartDao.getCart(userId);
                session.setAttribute(CART, cart);
                List<Order> orders = orderDao.getOrdersByUserId(userId);
                session.setAttribute(MY_ORDERS, orders);
                WishList wishList = wishListDao.getWishList(userId);
                session.setAttribute(WISH_LIST, wishList);
                List<Status> statuses = statusDao.getAll(lang);
                session.setAttribute(STATUSES, statuses);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}

