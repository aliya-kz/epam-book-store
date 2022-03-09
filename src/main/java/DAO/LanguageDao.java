package dao;

import entity.Lang;

import java.util.List;

public interface LanguageDao extends BaseDao <Lang> {

        int addEntity (Lang lang);
        List<Lang> getAll();
        int deleteById (long id);
}

