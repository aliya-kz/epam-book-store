package dao.impl;

import dao.OrderDao;
import dao.db_connection.ConnectionPool;
import entity.Address;
import entity.Book;
import entity.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static dao.DaoConstants.*;


public class OrderDaoImpl implements OrderDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String SELECT_ORDER = "SELECT o.*, ob.book_id, ob.quantity, cs.card_id, cs.card_number, a.address " +
            "from orders o left join order_books ob on o.id = ob.order_id left join addresses a " +
            " on a.address_id=o.address_id left join cards cs on cs.card_id=o.card_id where o.id = ?";

    private final static String INSERT_ORDER = "INSERT into orders (user_id, cost, status_id, date, address_id, card_id) values " +
            "(?, ?, ?, ?, ?, ?);";

    private final static String INSERT_ORDER_BOOKS = "INSERT into order_books (order_id, book_id, quantity) values (?, ?, ?);";

    private final static String SELECT_ALL_ORDERS = "SELECT id from orders;";

    private final static String SET_STATUS_SQL = "UPDATE orders set status_id = ? WHERE id = ?;";

    private final static String SELECT_USER_ORDERS = "SELECT id from orders WHERE user_id = ?;";


    @Override
    public boolean deleteById(long id) {

        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }

    @Override
    public boolean deleteByIdLang(long id, String lang) {

        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }

    @Override
    public boolean addEntity(Order order) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement insertOrder = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertOrderBooks = connection.prepareStatement(INSERT_ORDER_BOOKS)) {
            connection.setAutoCommit(false);
            insertOrder.setLong(1, order.getUserId());
            insertOrder.setInt(2, order.getCost());
            insertOrder.setLong(3, order.getStatusId());
            insertOrder.setDate(4, order.getDate());
            insertOrder.setLong(5, order.getAddress().getId());
            insertOrder.setLong(6, order.getCardId());
            insertOrder.executeUpdate();
            long orderId = 0;
            ResultSet rs = insertOrder.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getLong(ID);
            }

            Map<Book, Integer> orderItems = order.getOrderItems();
            for (Book book : orderItems.keySet()) {
                insertOrderBooks.setLong(1, orderId);
                insertOrderBooks.setLong(2, book.getId());
                insertOrderBooks.setInt(3, orderItems.get(book));
                insertOrderBooks.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                LOGGER.warn(ROLLED_BACK_MESSAGE);
                connection.rollback();
            } catch (SQLException excep) {
                LOGGER.warn(excep);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.info(e);
            }
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ORDERS);
            while (resultSet.next()) {
                long id = resultSet.getLong(ID);
                Order order = getOrderById(id);
                orders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOGGER.info(ex);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orders.stream()
                .sorted(Comparator.comparingLong(Order::getId))
                .collect(Collectors.toList());
    }

    public boolean updateStatus(long orderId, long statusId) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;

        try (PreparedStatement statement = connection.prepareStatement(SET_STATUS_SQL)) {
            statement.setLong(1, statusId);
            statement.setLong(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            result = false;
            LOGGER.error(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public Order getOrderById(long orderId) {
        Order order = new Order();
        Map<Book, Integer> items = new HashMap<>();
        order.setId(orderId);
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ORDER)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (order.getUserId() < 1) {
                    order.setUserId(resultSet.getInt(USER_ID));
                    Address address = new Address();
                    address.setId(resultSet.getInt(ADDRESS_ID));
                    address.setUserId(resultSet.getInt(USER_ID));
                    address.setAddress(resultSet.getString(ADDRESS));
                    order.setAddress(address);
                    order.setCost(resultSet.getInt(COST));
                    order.setDate(resultSet.getDate(DATE));
                    order.setStatusId(resultSet.getInt(STATUS_ID));
                    order.setCardNumber(resultSet.getString(CARD_NUMBER));
                    Book book = new Book(resultSet.getInt(BOOK_ID));
                    items.put(book, resultSet.getInt(QUANTITY));
                } else {
                    Book book = new Book(resultSet.getInt(BOOK_ID));
                    items.put(book, resultSet.getInt(QUANTITY));
                }
                order.setOrderItems(items);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOGGER.warn(ex);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return order;
    }

    public List<Order> getOrdersByUserId(long userId) {
        List<Order> orders = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_USER_ORDERS)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order order = getOrderById(resultSet.getInt(ID));
                orders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            LOGGER.info(ex);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orders.stream()
                .sorted(Comparator.comparingLong(Order::getId).reversed())
                .collect(Collectors.toList());
    }

}
