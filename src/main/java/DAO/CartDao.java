package DAO;

import entity.Cart;

import java.util.List;

public interface CartDao extends BaseDao <Cart> {
        int addEntity (Cart cart);
        List<Cart> getAll ();
        int deleteById (int id);
    int createCart (String email);
    int addToCart (int cartId, String isbn);
    int deleteFromCart (int cartId, String isbn);
    int getCartCost (int cartId);
}
