package dao;

import entity.Card;

public interface CardDao extends BaseDao <Card> {

    @Override
    int addEntity(Card card);

    int deleteById (long id);
}
