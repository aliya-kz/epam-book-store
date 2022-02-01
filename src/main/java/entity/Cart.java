package entity;

import java.util.HashMap;
import java.util.Map;

public class Cart extends Entity {
    private int id;
    private int userId;
    private Map<Book, Integer> cartItems;

    public Cart(int userId) {
        this.userId = userId;
    }

    public Cart() {
        cartItems = new HashMap<>();
    }

    public Map<Book, Integer> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<Book, Integer> cartItems) {
        this.cartItems = cartItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {

        this.userId = userId;
    }

    @Override
    public int compareTo(Entity o) {
        return 0;
    }
}