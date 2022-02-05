package service;

import DAO.*;
import entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class LoginService implements Service {
    private static SqlDaoFactory factory = SqlDaoFactory.getInstance();
    private LanguageDao languageDao = factory.getLanguageDao();
    private UserDao userDao= factory.getUserDao();
    private BookDao bookDao = factory.getBookDao();
    private CategoryDao categoryDao = factory.getCategoryDao();
    private AuthorDao authorDao = factory.getAuthorDao();
    private FormatDao formatDao = factory.getFormatDao();
    private CartDao cartDao = factory.getCartDao();
    private WishListDao wishListDao = factory.getWishListDao();
    private OrderDao orderDao = factory.getOrderDao();
    private StatusDao statusDao = factory.getStatusDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        String locale = (String) session.getAttribute("locale");
        String lang = locale.substring(0, 2);
        int userId = userDao.validateUser(email, password);
        if (userId < 1) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/logIn.jsp?msg=wrong");
            dispatcher.forward(request, response);
        } else {
            User user = userDao.getUser(userId);
            if (user.isBlocked()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/logIn.jsp?msg=blocked");
                dispatcher.forward(request, response);
            } else if (user.isAdmin()) {
                List <Status> statuses = statusDao.getAll(lang);
                session.setAttribute("statuses", statuses);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/admin/welcomeAdmin.jsp");
                dispatcher.forward(request, response);
            } else {
                session.setAttribute("user", user);
                Cart cart = cartDao.getCart(userId);
                session.setAttribute("cart", cart);
                List <Order> orders = orderDao.getOrdersByUserId(userId);
                session.setAttribute("myOrders", orders);
                WishList wishList = wishListDao.getWishList(userId);
                session.setAttribute("wishList", wishList);
                List <Status> statuses = statusDao.getAll(lang);
                session.setAttribute("statuses", statuses);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}

