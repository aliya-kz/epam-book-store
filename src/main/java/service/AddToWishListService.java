package service;

import dao.WishListDao;
import dao.impl.WishListDaoImpl;
import entity.Book;
import entity.User;
import entity.WishList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static service.GeneralConstants.*;


public class AddToWishListService implements Service {

    private final WishListDao wishListDao = new WishListDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        int bookId = Integer.parseInt(request.getParameter(ID));
        WishList wishList = (WishList) session.getAttribute(WISH_LIST);
        String uri = request.getParameter(URI);
        if (wishList.getBooks().contains(new Book(bookId))) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
        }
        wishListDao.addToWishList(user.getId(), bookId);
        wishList = wishListDao.getWishList(user.getId());
        session.setAttribute(WISH_LIST, wishList);

        RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?" + MESSAGE + "=" + ADDED);
        dispatcher.forward(request, response);
    }
}
