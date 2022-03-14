package dao;

import entity.Order;
import java.util.List;

public interface OrderDao extends BaseDao {

    boolean addEntity(Order order);
    List <Order> getAll ();
    boolean updateStatus (long orderId, long statusId);
    Order getOrderById (long id);
    List <Order> getOrdersByUserId(long userId);
}
