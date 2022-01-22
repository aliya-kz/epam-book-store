package DAO;

import entity.Cart;
import entity.Order;

import java.util.List;

public interface OrderDao extends BaseDao <Order> {
    int addEntity(Order order);
    List <Order> getAll ();
    int deleteById (int id);

    String getStatus (int orderId);
    int setStatus (int id, String orderStatus);
    List <Integer> bookIds (int orderId);
    Order getOrderById (int id);
    List <Integer> getAllOrders (int userId);
    int orderPrice (Order order);
    List <Order> getAllOrders();
}
