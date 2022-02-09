package DAO;

import entity.Book;

import java.util.List;


public interface BookDao extends BaseDao <Book> {
        int addEntity (Book book);
        int deleteById (int id);
        List<Book> getAll(String lang);
        List <Book> findBooksByAuthorIsbnOrTitle (String search);
        int deleteBookAuthors (int bookId);
        int setBookAuthors (int bookId, List <Integer> authorIds);
}
