package dao;
import entity.Cart;



public interface CartDao extends BaseDao <Cart> {

        boolean addEntity (Cart cart);
        Cart getCart (long userId);
        boolean deleteFromTable(long userId, long bookId);
        boolean addToCart (long userId, long bookId, int quantity);
        boolean updateQuantity(long bookId, long userId, int quantity);
}
