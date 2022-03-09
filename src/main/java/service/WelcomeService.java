package service;

import dao.*;
import dao.impl.*;
import entity.Cart;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static service.GeneralConstants.*;


public class WelcomeService implements Service {
    private final LanguageDao languageDao = new LanguageDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final CategoryDao categoryDao = new CategoryDaoImpl();
    private final AuthorDao authorDao = new AuthorDaoImpl();
    private final FormatDao formatDao = new FormatDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String locale = request.getParameter(LOCALE);
        session.setAttribute(LOCALE, locale);
        String lang = locale.substring(0, 2);
        session.setAttribute(BOOKS, bookDao.getAll(lang));
        session.setAttribute(AUTHORS, authorDao.getAll(lang));
        session.setAttribute(FORMATS, formatDao.getAll(lang));
        session.setAttribute(CATEGORIES, categoryDao.getAll(lang));
        session.setAttribute(LANGUAGES, languageDao.getAll());
        session.setAttribute(USERS, userDao.getAll());
        Cart cart = new Cart();
        session.setAttribute(CART, cart);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
        dispatcher.forward(request, response);
    }
}


