package DAO;
import entity.Cart;



public interface CartDao extends BaseDao <Cart> {
        int addEntity (Cart cart);
        Cart getCart (int userId);
        int deleteFromTable(int userId, int bookId);
        int addToCart (int userId, int bookId, int quantity);
        int updateQuantity(int bookId, int userId, int quantity);
}
