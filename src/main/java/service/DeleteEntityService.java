package service;
import DAO.*;
import entity.Book;
import entity.Cart;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class DeleteEntityService implements Service{
    SqlDaoFactory factory = SqlDaoFactory.getInstance();
    UserDao userDao = factory.getUserDao();
    CardDao cardDao = factory.getCardDao();
    AddressDao addressDao = factory.getAddressDao();
    CartDao cartDao = factory.getCartDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        String table = request.getParameter("table");
        String lang = request.getParameter("lang");
        String uri = request.getParameter("uri");

        switch (table) {
            case "cards":
                cardDao.deleteById(id);
                break;
            case "addresses":
                addressDao.deleteById(id);
                break;
            case "carts":
                Cart cart = (Cart) session.getAttribute("cart");
                if (user == null) {
                    cart.getCartItems().remove(new Book(id));
                } else {
                    cartDao.deleteById(id, user.getId());
                    cart = cartDao.getCart(user.getId());
                }
                session.setAttribute("cart", cart);
                break;
            default:
                if (lang == null) {
                    userDao.deleteById(table, id);
                    break;
                } else {
                    userDao.deleteByIdLang(table, id, lang);
                    break;
                }
        }
        System.out.println("deleted");
        user = userDao.getUser(user.getId());
        session.setAttribute("user", user);
        request.getRequestDispatcher(uri).forward(request, response);
    }
}
