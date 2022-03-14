package service;

import dao.BookDao;
import dao.CartDao;
import dao.UserDao;
import dao.impl.BookDaoImpl;
import dao.impl.CartDaoImpl;
import dao.impl.UserDaoImpl;
import entity.Book;
import entity.Cart;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
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
    private CartDao cartDao = new CartDaoImpl();

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
        InputStream is = ((Part) part).getInputStream();
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
        String languageCode = locale.substring(0, 2);
        List<Book> books = bookDao.getAll(languageCode);
        session.setAttribute(BOOKS, books);
    }

    public void updateUserAttribute(HttpSession session, long userId) {
        User user = userDao.getUser(userId);
        session.setAttribute(USER, user);
    }

    public void updateCartAttribute(HttpSession session, long userId) {
        Cart cart = cartDao.getCart(userId);
        session.setAttribute(CART, cart);
    }
}
