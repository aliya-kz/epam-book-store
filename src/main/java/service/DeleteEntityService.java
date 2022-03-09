package service;

import dao.*;
import dao.impl.CartDaoImpl;
import dao.impl.UserDaoImpl;
import dao.impl.WishListDaoImpl;
import entity.Book;
import entity.Cart;
import entity.User;
import entity.WishList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static service.GeneralConstants.*;


public class DeleteEntityService implements Service {

    private final static SqlDaoFactory daoFactory = SqlDaoFactory.getInstance();
    private final UserDao userDao = new UserDaoImpl();
    private final CartDao cartDao = new CartDaoImpl();
    private final WishListDao wishListDao = new WishListDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        int id = Integer.parseInt(request.getParameter(ID));
        String table = request.getParameter(TABLE);
        String lang = request.getParameter(LANGUAGE);
        String uri = request.getParameter(URI);
        Cart cart = (Cart) session.getAttribute(CART);
        Map<Book, Integer> cartItems = cart.getCartItems();
        if (user == null) {
            cartItems.entrySet()
                    .removeIf(entry -> entry.getKey().equals(new Book(id)));
            session.setAttribute(CART, cart);
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
        } else {
            long userId = user.getId();
            if (table.equals(WISH_LISTS)) {
                wishListDao.deleteFromTable(userId, id);
                WishList wishList = wishListDao.getWishList(userId);
                session.setAttribute(WISH_LIST, wishList);
            } else if (table.equals(CARTS)) {
                cartDao.deleteFromTable(id, userId);
                cart = cartDao.getCart(userId);
                session.setAttribute(CART, cart);
            } else {
                BaseDao dao = daoFactory.getDao(table);
                if (lang == null) {
                    dao.deleteById(id);
                } else {
                    dao.deleteByIdLang(id, lang);
                }
                user = userDao.getUser(userId);
                session.setAttribute(USER, user);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
        }
    }
}
