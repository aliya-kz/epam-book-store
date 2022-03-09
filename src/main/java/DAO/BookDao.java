package dao;

import entity.Book;

import java.util.List;
import java.util.Map;

public interface BookDao extends BaseDao <Book> {
        int addEntity (Book book);
        int deleteById (long id);
        long purchaseBooks(Map<Book, Integer> items);
        int returnBooks(Map<Book, Integer> cartItems, long errorBookId);
        List<Book> getAll(String lang);
        int deleteBookAuthors (long bookId);
        int setBookAuthors (long bookId, List <Integer> authorIds);
        List<Book> filterByCategory (List<Book> books, long[] categoryId);
        int setByteImage (long id, String url);
        List<Book> filterByFormat (List<Book> books, long[] formatId);
        List<Book> filterByPublLang (List<Book> books, String[] publLang);
}
