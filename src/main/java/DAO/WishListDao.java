package DAO;

import entity.WishList;

public interface WishListDao extends BaseDao <WishList> {
    int addToWishList (int userId, int bookId);
    int deleteFromWishList (int userId, int bookId);
    WishList getWishList (int userId);
}
