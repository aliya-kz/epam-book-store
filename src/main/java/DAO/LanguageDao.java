package dao;

import entity.Lang;

import java.util.List;

public interface LanguageDao extends BaseDao <Lang> {

        boolean addEntity (Lang lang);
        List<Lang> getAll();
        boolean deleteById (long id);
}

