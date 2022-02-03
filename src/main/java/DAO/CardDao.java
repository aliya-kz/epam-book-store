package DAO;

import entity.Card;

public interface CardDao extends BaseDao <Card> {

    @Override
    int addEntity(Card card);

    int deleteById (int id);
}
