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
        if (user == null) {
            deleteFromCartNotUser(request, response, id);
        } else {
            long userId = user.getId();
            if (table.equals(WISH_LISTS)) {
                deleteFromWishList(session, userId, id);
            } else if (table.equals(CARTS)) {
                deleteFromCart(session, userId, id);
            } else {
                BaseDao dao = daoFactory.getDao(table);
                if (lang == null) {
                    dao.deleteById(id);
                } else {
                    dao.deleteByIdLang(id, lang);
                }
                HelperClass.getInstance().updateUserAttribute(session, userId);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
        }
    }

    public void deleteFromCartNotUser(HttpServletRequest request, HttpServletResponse response, int bookId)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String uri = request.getParameter(URI);
        Cart cart = (Cart) session.getAttribute(CART);
        Map<Book, Integer> cartItems = cart.getCartItems();
        cartItems.entrySet()
                .removeIf(entry -> entry.getKey().equals(new Book(bookId)));
        session.setAttribute(CART, cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
        dispatcher.forward(request, response);
    }

    public void deleteFromWishList (HttpSession session, long userId, long bookId)
            throws ServletException, IOException {
        wishListDao.deleteFromTable(userId, bookId);
        WishList wishList = wishListDao.getWishList(userId);
        session.setAttribute(WISH_LIST, wishList);
    }

    public void deleteFromCart (HttpSession session, long userId, long bookId)
            throws ServletException, IOException {
        cartDao.deleteFromTable(bookId, userId);
        Cart cart = cartDao.getCart(userId);
        session.setAttribute(CART, cart);
    }
}
