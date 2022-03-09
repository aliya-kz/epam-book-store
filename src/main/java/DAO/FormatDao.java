package DAO;

import entity.Format;
import java.util.List;

public interface FormatDao extends BaseDao <Format>{

    int addEntity (Format format);
    List<Format> getAll (String lang);
    int deleteById (long id);
}
