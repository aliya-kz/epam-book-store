package DAO.impl;

import DAO.OrderDao;
import DAO.db_connection.ConnectionPool;
import entity.Address;
import entity.Book;
import entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static String GET_ID = "SELECT max(id) FROM orders where user_id = ?;";

    private static String SELECT_ORDER = "SELECT o.*, ob.book_id, ob.quantity, a.address from orders o " +
            "left join order_books ob on o.id = ob.order_id left join addresses a " +
            "on a.address_id=o.address_id where o.id = ?";

    private static String INSERT_ORDER = "INSERT into orders (user_id, cost, status_id, date, address_id) values " +
            "(?, ?, ?, ?, ?);";

    private static String INSERT_ORDER_BOOKS = "INSERT into order_books (order_id, book_id, quantity) values (?, ?, ?);";

    private static String SELECT_ALL_ORDERS = "SELECT * from orders;";


    private static String SELECT_QUANTITY_SQL = "SELECT id, status FROM orders where id = ?;";


    private static String SET_STATUS_SQL = "UPDATE orders set status = ? WHERE id = ?;";

    private static String SELECT_BOOKS_SQL = "SELECT book_id FROM order_books where order_id = ?;";

    private static String SELECT_ALL_BY_ID_SQL = "SELECT * FROM order where order_id = ?;";

    private static String ALL_ORDERS_SQL = "SELECT order_id FROM orders where user_id = ?;";

    private static String SELECT_USER_ORDERS = "SELECT id from orders WHERE user_id = ?;";



    @Override
    public int addEntity(Order order) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        try {
            statement = connection.prepareStatement(INSERT_ORDER);
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getCost());
            statement.setInt(3, order.getStatusId());
            statement.setDate(4, order.getDate());
            statement.setInt(5, order.getAddress().getId());
            statement.executeUpdate();
            int orderId = 0;

            statement1 = connection.prepareStatement(GET_ID);
            statement1.setInt(1, order.getUserId());
            ResultSet resultSet = statement1.executeQuery();
            while (resultSet.next()) {
                orderId = resultSet.getInt("max");
            }

            Map<Book, Integer> orderItems = order.getOrderItems();
            for (Book book: orderItems.keySet()) {
                statement2 = connection.prepareStatement(INSERT_ORDER_BOOKS);
                statement2.setInt(1, orderId);
                statement2.setInt(2, book.getId());
                statement2.setInt(3, orderItems.get(book));
                result = statement2.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public List<Order> getAll() {
        List <Order> orders = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_ALL_ORDERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = getOrderById(resultSet.getInt("id"));
                orders.add(order);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.info(ex);
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return orders;
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

    public Order getOrderById (int orderId) {
        Order order = new Order();
        Map <Book, Integer> items = new HashMap<>();
        order.setId(orderId);
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_ORDER);
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (order.getUserId() < 1) {
                    order.setUserId(resultSet.getInt("user_id"));
                    Address address = new Address();
                    address.setId(resultSet.getInt("address_id"));
                    address.setUserId(resultSet.getInt("user_id"));
                    address.setAddress(resultSet.getString("address"));
                    order.setAddress(address);
                    order.setCost(resultSet.getInt("cost"));
                    order.setDate(resultSet.getDate("date"));
                    order.setStatusId(resultSet.getInt("status_id"));
                    Book book = new Book (resultSet.getInt("book_id"));
                    items.put(book, resultSet.getInt("quantity"));
                } else {
                    Book book = new Book(resultSet.getInt("book_id"));
                    items.put(book, resultSet.getInt("quantity"));
                }
                order.setOrderItems(items);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.warn(ex);
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);}
        return order;
    }

    public List <Order> getOrdersByUserId(int userId) {
        List <Order> orders = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_USER_ORDERS);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = getOrderById(resultSet.getInt("id"));
                orders.add(order);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.info(ex);
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return orders;
    }


    public  static void main (String [] args) {
        OrderDao i =  new OrderDaoImpl();
    }
}
