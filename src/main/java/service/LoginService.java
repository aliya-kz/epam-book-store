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
    private final HelperClass helperClass = HelperClass.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();
        boolean userIsValid = userDao.validateUser(email, password);
        if (!userIsValid) {
            helperClass.forwardToUriWithMessage(request, response,LOGIN_URI, WRONG);
        } else {
            long userId = userDao.getIdByEmail(email);
            User user = userDao.getUser(userId);
            if (user.isBlocked()) {
                helperClass.forwardToUriWithMessage(request, response,LOGIN_URI, BLOCKED);
            } else if (user.isAdmin()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(ADMIN_BOOKS_URI);
                dispatcher.forward(request, response);
            } else {
                setUserAttributes(session, user);
                RequestDispatcher dispatcher = request.getRequestDispatcher(INDEX_URI);
                dispatcher.forward(request, response);
            }
        }
    }

    public void setUserAttributes(HttpSession session, User user) {
        String locale = (String) session.getAttribute(LOCALE);
        String languageCode = locale.substring(0, 2);
        long id = user.getId();
        session.setAttribute(USER, user);
        Cart cart = cartDao.getCart(id);
        session.setAttribute(CART, cart);
        List<Order> orders = orderDao.getOrdersByUserId(id);
        session.setAttribute(MY_ORDERS, orders);
        WishList wishList = wishListDao.getWishList(id);
        session.setAttribute(WISH_LIST, wishList);
        List<Status> statuses = statusDao.getAll(languageCode);
        session.setAttribute(STATUSES, statuses);
    }
}

