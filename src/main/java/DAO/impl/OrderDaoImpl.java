package DAO.impl;

import DAO.OrderDao;
import DAO.db_connection.ConnectionPool;
import entity.Cart;
import entity.Entity;
import entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static String SELECT_QUANTITY_SQL = "SELECT id, status FROM orders where id = ?;";

    private static String SET_STATUS_SQL = "UPDATE orders set status = ? WHERE id = ?;";

    private static String SELECT_BOOKS_SQL = "SELECT book_id FROM order_books where order_id = ?;";

    private static String SELECT_ALL_BY_ID_SQL = "SELECT * FROM order where order_id = ?;";

    private static String ALL_ORDERS_SQL = "SELECT order_id FROM orders where user_id = ?;";


    @Override
    public int addEntity(Order order) {
        return 0;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }

    public String getStatus(int orderId) {
        String status = "";
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(SELECT_QUANTITY_SQL);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                status = resultSet.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return status;
    }

    public int setStatus(int orderId, String orderStatus) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        int result = 0;

        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(SET_STATUS_SQL);
            statement.setString(1, orderStatus);
            statement.setInt(2, orderId);
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public List <Integer> bookIds(int orderId) {
        List <Integer> books = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(SELECT_BOOKS_SQL);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt(1);
                books.add(bookId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return books;
    }

    public Order getOrderById(int orderId) {
        Order order = new Order();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        /*
        try { statement = connection.prepareStatement("select pp.* from orders pp left join order_books p on pp.id=p.order_id;");
           /* statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order.setAddress(resultSet.getString("address"));
                order.setUserId(resultSet.getInt("user_id"));
                order.setStatus(resultSet.getString("status"));
                order.setDate(resultSet.getDate("date"))
                order.setUserId(resultSet.getInt("user_id"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);}*/
                return null;
    }

    public List<Integer> getAllOrders(int userId) {
        List <Integer> orders = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(ALL_ORDERS_SQL);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt(1);
                orders.add(orderId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return orders;
    }
//TODO
    public int orderPrice(Order order) {
        return -1;
    }

    @Override
    public List<Order> getAllOrders() {
        List <Order> orders = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement("SELECT * FROM ORDERS");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
               order.setId(resultSet.getInt("id"));

               //TODO
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return orders;
    }

    public  static void main (String [] args) {
        OrderDaoImpl i =  new OrderDaoImpl();
        i.setStatus(2, "DELIVERED");
    }
}
