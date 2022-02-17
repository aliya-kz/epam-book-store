package DAO;

import entity.Book;
import entity.Cart;

import java.util.List;
import java.util.Map;

public interface BookDao extends BaseDao <Book> {
        int addEntity (Book book);
        int deleteById (int id);
        int purchaseBooks(Map<Book, Integer> items);
        int returnBooks(Map<Book, Integer> cartItems, int errorBookId);
        List<Book> getAll(String lang);
        List <Book> findBooksByAuthorIsbnOrTitle (String search);
        int deleteBookAuthors (int bookId);
        int setBookAuthors (int bookId, List <Integer> authorIds);
        List<Book> filterByCategory (List<Book> books, int[] categoryId);
        int setByteImage (int id, String url);
        List<Book> filterByFormat (List<Book> books, int[] formatId);
        List<Book> filterByPublLang (List<Book> books, String[] publLang);
}
