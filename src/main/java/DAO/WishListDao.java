package dao;

import entity.WishList;

public interface WishListDao extends BaseDao <WishList> {

    boolean addToWishList (long userId, long bookId);
    WishList getWishList (long userId);
}
