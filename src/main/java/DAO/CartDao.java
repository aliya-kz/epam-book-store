package DAO;
import entity.Cart;



public interface CartDao extends BaseDao <Cart> {

        int addEntity (Cart cart);
        Cart getCart (long userId);
        int deleteFromTable(long userId, long bookId);
        int addToCart (long userId, long bookId, int quantity);
        int updateQuantity(long bookId, long userId, int quantity);
}
