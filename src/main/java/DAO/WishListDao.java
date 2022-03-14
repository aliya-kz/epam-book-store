package dao;

import entity.WishList;

public interface WishListDao extends BaseDao {

    boolean addToWishList (long userId, long bookId);
    WishList getWishList (long userId);
}
