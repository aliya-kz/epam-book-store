package service;

import DAO.*;
import DAO.impl.*;
import entity.Cart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class WelcomeService implements Service {
    private final LanguageDao languageDao = new LanguageDaoImpl();
    private final UserDao userDao= new UserDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final CategoryDao categoryDao = new CategoryDaoImpl();
    private final AuthorDao authorDao = new AuthorDaoImpl();
    private final FormatDao formatDao = new FormatDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = request.getParameter("locale");
        session.setAttribute("locale", locale);
        String lang = locale.substring(0, 2);
        session.setAttribute("books", bookDao.getAll(lang));
        session.setAttribute("authors", authorDao.getAll(lang));
        session.setAttribute("formats", formatDao.getAll(lang));
        session.setAttribute("categories", categoryDao.getAll(lang));
        session.setAttribute("langs", languageDao.getAll());
        session.setAttribute("users", userDao.getAll());
        Cart cart = new Cart();
        session.setAttribute("cart", cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
        dispatcher.forward(request, response);
    }
}


