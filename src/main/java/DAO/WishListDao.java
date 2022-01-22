package DAO;

import entity.WishList;

import java.util.List;

public interface WishListDao extends BaseDao <WishList> {
    int addEntity(WishList wishList);

    List<WishList> getAll ();

    int deleteById (int id);

    int addToWishList (int lisId, int bookId);
    int deleteFromWishList (int listId, int bookId);
}
