package DAO;

import entity.WishList;

public interface WishListDao extends BaseDao <WishList> {
    int addToWishList (int userId, int bookId);
    public int deleteFromTable(int userId, int bookId);
    WishList getWishList (int userId);
}
