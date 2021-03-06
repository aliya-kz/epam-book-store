package service;

import dao.*;
import dao.impl.*;
import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static service.GeneralConstants.*;


public class HelperClass {

    private final BookDao bookDao = new BookDaoImpl();
    private static HelperClass instance = new HelperClass();
    private final UserDao userDao = new UserDaoImpl();
    private final CartDao cartDao = new CartDaoImpl();
    private final OrderDao orderDao = new OrderDaoImpl();
    private final WishListDao wishListDao = new WishListDaoImpl();
    private final LanguageDao languageDao = new LanguageDaoImpl();
    private final CategoryDao categoryDao = new CategoryDaoImpl();
    private final AuthorDao authorDao = new AuthorDaoImpl();
    private final FormatDao formatDao = new FormatDaoImpl();
    private final StatusDao statusDao = new StatusDaoImpl();

    private HelperClass() {
    }

    public static HelperClass getInstance() {
        return instance;
    }

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    public byte[] partToBytes(HttpServletRequest request, String file) throws IOException {
        Part part = null;
        try {
            part = request.getPart(file);
        } catch (ServletException | IOException e) {
            LOGGER.info(e);
            e.printStackTrace();
        }
        InputStream is = part.getInputStream();
        byte[] bytes = is.readAllBytes();
        return bytes;
    }

    public void forwardToUriWithMessage(HttpServletRequest request, HttpServletResponse response, String uri, String message)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri + SIGN_QUESTION + MESSAGE + SIGN_EQUALS + message);
        dispatcher.forward(request, response);
    }

    public void updateBooksAttribute(HttpSession session) {
        String locale = (String) session.getAttribute(LOCALE);
        ServletContext context = session.getServletContext();
        String languageCode = locale.substring(0, 2);
        List<Book> books = bookDao.getAll(languageCode);
        context.setAttribute(BOOKS, books);
    }

    public void updateUserAttribute(HttpSession session, long userId) {
        User user = userDao.getUser(userId);
        session.setAttribute(USER, user);
    }

    public void updateAllUsersAttribute(HttpSession session) {
        List<User> users = userDao.getAll();
        session.setAttribute(USERS, users);
    }

    public void updateContextAttributes (ServletContext context, String lang) {
        context.setAttribute(STATUSES, statusDao.getAll(lang));
        context.setAttribute(BOOKS, bookDao.getAll(lang));
        context.setAttribute(AUTHORS, authorDao.getAll(lang));
        context.setAttribute(FORMATS, formatDao.getAll(lang));
        context.setAttribute(CATEGORIES, categoryDao.getAll(lang));
        context.setAttribute(LANGUAGES, languageDao.getAll());
        context.setAttribute(USERS, userDao.getAll());
    }

    public void updateUserAttributes(HttpSession session, long userId) {
       updateUserAttribute(session, userId);
       updateCartAttribute(session, userId);
        List<Order> orders = orderDao.getOrdersByUserId(userId);
        session.setAttribute(MY_ORDERS, orders);
        WishList wishList = wishListDao.getWishList(userId);
        session.setAttribute(WISH_LIST, wishList);
    }

    public void updateCartAttribute(HttpSession session, long userId) {
        Cart cart = cartDao.getCart(userId);
        session.setAttribute(CART, cart);
    }
}
