package DAO;

import entity.Book;

import java.util.List;
import java.util.Map;

public interface BookDao extends BaseDao <Book> {
        int addEntity (Book book);
        List <Book> getAll ();
        int deleteById (int id);

        List<Book> selectBooksByCategory (int categoryID, String lang);
        List <Book> findBooksByAuthorIsbnOrTitle (String search);
        Map<String, Object> getParamList (Book book);
}
