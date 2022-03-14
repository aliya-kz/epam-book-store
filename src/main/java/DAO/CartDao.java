package dao;
import entity.Cart;



public interface CartDao extends BaseDao <Cart> {

        public boolean deleteCart(long userId);
        boolean deleteById (long cartItemId);
        boolean addEntity (Cart cart);
        Cart getCart (long userId);
        boolean addToCart (long userId, long bookId, int quantity);
        boolean updateQuantity(long bookId, long userId, int quantity);
}
