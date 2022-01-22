package DAO.impl;

import DAO.CartDao;
import entity.Cart;


import java.util.List;

public class CartDaoImpl implements CartDao {

    @Override
    public int addEntity(Cart cart) {
        return 0;
    }

    @Override
    public List<Cart> getAll() {
        return null;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }

    @Override
    public int createCart(String email) {
        return 0;
    }

    @Override
    public int addToCart(int cartId, String isbn) {
        return 0;
    }

    @Override
    public int deleteFromCart(int cartId, String isbn) {
        return 0;
    }

    @Override
    public int getCartCost(int cartId) {
        return 0;
    }

}
