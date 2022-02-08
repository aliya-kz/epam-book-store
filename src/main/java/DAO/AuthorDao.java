package DAO;
import entity.Author;
import java.util.List;

public interface AuthorDao extends BaseDao <Author> {
    int addEntity (Author author);
    int addTranslation (Author author);
    List<Author> getAll (String lang);
    int deleteById (int id);
    int deleteByIdLang (int id, String lang);
    List <Integer> searchAuthors (String search);
}
