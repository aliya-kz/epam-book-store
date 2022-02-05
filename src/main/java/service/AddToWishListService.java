package service;

import DAO.SqlDaoFactory;
import DAO.WishListDao;
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
import java.util.List;


public class AddToWishListService implements Service {
private final WishListDao wishListDao = SqlDaoFactory.getInstance().getWishListDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    int bookId = Integer.parseInt(request.getParameter("id"));
    Book wlBook = new Book(bookId);
    WishList wishList = (WishList) session.getAttribute("wishList");
    if (!wishList.getBooks().contains(wlBook)) {
        List<Book> allBooks = (List<Book>) session.getAttribute("books");
        for (Book book : wishList.getBooks()) {
            for (Book listBook : allBooks) {
                if (book.getId() == listBook.getId()) {
                    book = listBook;
                    wishListDao.addToWishList(user.getId(), bookId);
                }
            }
        }
    }
    wishList = wishListDao.getWishList(user.getId());
    session.setAttribute("wishList", wishList);
    String uri = request.getParameter("uri");
    RequestDispatcher dispatcher = request.getRequestDispatcher(uri + "?msg=added");
    dispatcher.forward(request, response);
    }
}
