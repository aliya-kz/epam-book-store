package service;

import dao.*;
import entity.Book;
import entity.Cart;
import entity.User;


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

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long id = Long.parseLong(request.getParameter(ID));
        String table = request.getParameter(TABLE);
        String lang = request.getParameter(LANGUAGE);
        String uri = request.getParameter(URI);
        String wishListItemId = request.getParameter(WISH_LIST_ITEM_ID);

        if (wishListItemId != null) {
            id = Long.parseLong(wishListItemId);
        }

        if (user == null) {
            deleteFromCartNotUser(session, id);
        } else {
            BaseDao dao = daoFactory.getDao(table);
            if (lang == null) {
                dao.deleteById(id);
            } else {
                dao.deleteByIdLang(id, lang);
            }
            HelperClass.getInstance().updateAllUserAttributes(session, user.getId());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
        dispatcher.forward(request, response);
    }

    public void deleteFromCartNotUser(HttpSession session, long bookId) {
        Cart cart = (Cart) session.getAttribute(CART);
        Map<Book, Integer> cartItems = cart.getCartItems();
        cartItems.entrySet()
                .removeIf(entry -> entry.getKey().equals(new Book(bookId)));
        session.setAttribute(CART, cart);
    }
}
