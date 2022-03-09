package DAO;

import entity.Order;
import java.util.List;

public interface OrderDao extends BaseDao <Order> {

    int addEntity(Order order);
    List <Order> getAll ();
    int updateStatus (long orderId, long statusId);
    Order getOrderById (long id);
    List <Order> getOrdersByUserId(long userId);
}
