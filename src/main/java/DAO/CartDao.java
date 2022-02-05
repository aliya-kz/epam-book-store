package DAO;
import entity.Cart;



public interface CartDao extends BaseDao <Cart> {
        int addEntity (Cart cart);
        Cart getCart (int userId);
        int deleteFromCart(int bookId, int userId);
        int deleteEntity (int userId);
        int addToCart (int userId, int bookId, int quantity);
        int updateQuantity(int bookId, int userId, int quantity);
}
