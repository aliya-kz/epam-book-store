package dao;

import entity.Card;

public interface CardDao extends BaseDao {

    boolean addEntity(Card card);

    boolean deleteById (long id);
}
