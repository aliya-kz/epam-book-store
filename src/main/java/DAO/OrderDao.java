package DAO;

import entity.Order;

import java.util.List;

public interface OrderDao extends BaseDao <Order> {
    int addEntity(Order order);
    List <Order> getAll ();
    int updateStatus (int orderId, int statusId);
    Order getOrderById (int id);
    List <Order> getOrdersByUserId(int userId);
}
