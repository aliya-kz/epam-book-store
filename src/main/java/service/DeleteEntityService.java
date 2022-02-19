package service;
import DAO.*;
import DAO.impl.CartDaoImpl;
import DAO.impl.UserDaoImpl;
import DAO.impl.WishListDaoImpl;
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


public class DeleteEntityService implements Service{

    private final SqlDaoFactory daoFactory = SqlDaoFactory.getInstance();
    private final UserDao userDao = new UserDaoImpl();
    private final CartDao cartDao = new CartDaoImpl();
    private final WishListDao wishListDao = new WishListDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        String table = request.getParameter("table");
        String lang = request.getParameter("lang");
        String uri = request.getParameter("uri");
        Cart cart = (Cart) session.getAttribute("cart");
        Map<Book, Integer> cartItems = cart.getCartItems();
        if (user == null) {
            cartItems.entrySet()
                    .removeIf(entry -> entry.getKey().equals(new Book(id)));
            session.setAttribute("cart", cart);
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
        } else {
            int userId = user.getId();
            if (table.equals("wish_lists")) {
                wishListDao.deleteFromTable(userId, id);
                WishList wishList = wishListDao.getWishList(userId);
                session.setAttribute("wishList", wishList);
            } else if (table.equals("carts")) {
                cartDao.deleteFromTable(id, userId);
                cart = cartDao.getCart(userId);
                session.setAttribute("cart", cart);
            } else {
                BaseDao dao = daoFactory.getDao(table);
                if (lang == null) {
                    dao.deleteById(id);
                } else {
                    dao.deleteByIdLang(id, lang);
                }
                user = userDao.getUser(userId);
                session.setAttribute("user", user);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
        }
    }
}
