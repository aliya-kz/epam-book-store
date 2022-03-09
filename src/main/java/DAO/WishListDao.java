package DAO;

import entity.WishList;

public interface WishListDao extends BaseDao <WishList> {

    int addToWishList (long userId, long bookId);
    int deleteFromTable(long userId, long bookId);
    WishList getWishList (long userId);
}
