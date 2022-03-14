package dao;
import entity.Author;
import java.util.List;

public interface AuthorDao extends BaseDao <Author> {

    boolean addEntity (Author author);
    boolean addTranslation (Author author);
    List<Author> getAll (String lang);
    boolean deleteById (long id);
    boolean deleteByIdLang (long id, String lang);
    List <Integer> searchAuthors (String search);
    boolean authorWithIdAndLangExists(Author author);
}
