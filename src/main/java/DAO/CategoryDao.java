package dao;

import entity.Category;

import java.util.List;

public interface CategoryDao extends BaseDao <Category> {

    List <Category> getAll (String lang);
    boolean addEntity(Category category);
    boolean deleteById (long id);
}
