package dao;

import entity.Card;

public interface CardDao extends BaseDao <Card> {

    @Override
    boolean addEntity(Card card);

    boolean deleteById (long id);
}
