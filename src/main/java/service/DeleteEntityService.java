package service;
import DAO.*;
import entity.Book;
import entity.Cart;
import entity.Category;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class DeleteEntityService implements Service{
    SqlDaoFactory factory = SqlDaoFactory.getInstance();
    UserDao userDao = factory.getUserDao();
    CardDao cardDao = factory.getCardDao();
    AddressDao addressDao = factory.getAddressDao();
    CartDao cartDao = factory.getCartDao();
    CategoryDao categoryDao = factory.getCategoryDao();
    AuthorDao authorDao = factory.getAuthorDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        String table = request.getParameter("table");
        String lang = request.getParameter("lang");
        String uri = request.getParameter("uri");
        Service service;
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
                        cartDao.deleteFromCart(id, user.getId());
                        cart = cartDao.getCart(user.getId());
                    }
                    session.setAttribute("cart", cart);
                    break;
                case "categories":
                    categoryDao.deleteById(id);
                    service = new GetAllCategoriesService();
                    service.execute(request, response);

                case "categories_lang":
                    categoryDao.deleteByIdLang("categories_lang", id, lang);
                    service = new GetAllCategoriesService();
                    service.execute(request, response);
                case "authors":
                    authorDao.deleteById(id);
                    service =new GetAllAuthorsService();
                    service.execute(request, response);
                case "authors_lang":
                    authorDao.deleteByIdLang("authors_lang", id, lang);
                    service =new GetAllAuthorsService();
                    service.execute(request, response);
            }

           /* if (user != null) {
                user = userDao.getUser(user.getId());
                session.setAttribute("user", user);

            }
            else {
                Cart cart = (Cart) session.getAttribute("cart");
                cart.getCartItems().remove(new Book(id));
            }
        request.getRequestDispatcher(uri).forward(request, response);*/
    }
}
