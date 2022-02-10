package DAO;

import entity.Lang;

import java.util.List;

public interface LanguageDao extends BaseDao <Lang> {

        int addEntity (Lang lang);
        List<Lang> getAll();
        int deleteById (int id);
}

