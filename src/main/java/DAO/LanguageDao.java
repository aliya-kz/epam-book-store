package dao;

import entity.Lang;

import java.util.List;

public interface LanguageDao extends BaseDao {

        List<Lang> getAll();
        boolean deleteById (long id);
}

