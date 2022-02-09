package service;

import DAO.WishListDao;
import DAO.impl.WishListDaoImpl;
import entity.Book;
import entity.User;
import entity.WishList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AddToWishListService implements Service {
private final WishListDao wishListDao = new WishListDaoImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int bookId = Integer.parseInt(request.getParameter("id"));
        WishList wishList = (WishList) session.getAttribute("wishList");
        String uri = request.getParameter("uri");
        for (Book book : wishList.getBooks()) {
            if (wishList.getBooks().indexOf(book) > -1) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
                dispatcher.forward(request, response);
            }
        }
        wishListDao.addToWishList(user.getId(), bookId);
        wishList = wishListDao.getWishList(user.getId());
        session.setAttribute("wishList", wishList);

        RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?msg=added");
        dispatcher.forward(request, response);
        }
    }
