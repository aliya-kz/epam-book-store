package DAO;

import entity.Category;

import java.util.List;

public interface CategoryDao extends BaseDao <Category> {

    List <Category> getAll (String lang);
    int addEntity(Category category);
    int deleteById (long id);
}
