package dao;

import entity.Format;
import java.util.List;

public interface FormatDao extends BaseDao {

    List<Format> getAll (String lang);
    boolean deleteById (long id);
}
