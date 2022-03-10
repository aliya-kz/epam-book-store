package dao;

import entity.Format;
import java.util.List;

public interface FormatDao extends BaseDao <Format>{

    boolean addEntity (Format format);
    List<Format> getAll (String lang);
    boolean deleteById (long id);
}
