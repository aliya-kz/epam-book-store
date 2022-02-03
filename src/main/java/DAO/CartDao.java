package DAO;
import entity.Cart;



public interface CartDao extends BaseDao <Cart> {
        int addEntity (Cart cart);
        Cart getCart (int userId);
        int deleteById(int bookId, int userId);
        int deleteCart (int userId);
        int addToCart (int userId, int bookId, int quantity);
        int deleteFromCart (int cartId, String isbn);
        int getCartCost (int cartId);
        int updateQuantity(int bookId, int userId, int quantity);
}
