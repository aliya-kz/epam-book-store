package dao.impl;

import dao.CartDao;
import dao.db_connection.ConnectionPool;
import entity.Book;
import entity.Cart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static dao.DaoConstants.*;


public class CartDaoImpl implements CartDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String SELECT_CART = "SELECT * FROM carts where user_id = ?";
    private final static String INSERT_CART = "INSERT into carts (user_id, book_id, quantity) values (?, ?, ?);";
    private final static String SELECT_BOOK = "SELECT quantity FROM carts where user_id = ? and book_id = ?;";
    private final static String UPDATE_QTY = "UPDATE carts set quantity = ? WHERE book_id = ? and user_id = ?;";
    private final static String DELETE_FROM_CART = "DELETE from carts WHERE book_id = ? AND user_id = ?;";
    private final static String DELETE_CART = "DELETE from carts WHERE user_id = ?;";


    @Override
    public Cart getCart(long userId) {
        Cart cart = new Cart(userId);
        Map<Book, Integer> cartItems = new HashMap<>();
        cart.setCartItems(cartItems);
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_CART);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book(resultSet.getInt(BOOK_ID));
                cartItems.put(book, resultSet.getInt(QUANTITY));
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return cart;
    }


    public boolean deleteFromTable(long bookId, long userId) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_FROM_CART);) {
            statement.setLong(1, bookId);
            statement.setLong(2, userId);
            statement.executeUpdate();
            close(statement);
        } catch (SQLException e) {
            LOGGER.info(e);
            result = false;
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }


    public boolean addToCart(long userId, long bookId, int quantity) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BOOK);
        PreparedStatement statement1 = connection.prepareStatement(UPDATE_QTY);
        PreparedStatement statement2 = connection.prepareStatement(INSERT_CART);) {
            connection.setAutoCommit(false);
            statement.setLong(1, userId);
            statement.setLong(2, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (quantity > 8) {
                    statement1.setInt(1, 8);
                } else {
                    statement1.setInt(1, quantity);
                }
                statement1.setLong(2, bookId);
                statement1.setLong(3, userId);
                statement1.executeUpdate();
            } else {
                statement2.setLong(1, userId);
                statement2.setLong(2, bookId);
                statement2.setInt(3, quantity);
                statement2.executeUpdate();
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    LOGGER.warn("Transaction rolled back");
                    result = false;
                    connection.rollback();
                } catch (SQLException excep) {
                    LOGGER.warn(excep);
                }
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
    public boolean updateQuantity(long bookId, long userId, int quantity) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QTY);) {
            statement.setInt(1, quantity);
            statement.setLong(2, bookId);
            statement.setLong(3, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            result = false;
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public boolean deleteById(long id) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CART);) {
            statement.setLong(1, id);
            statement.executeUpdate();
            close(statement);
        } catch (SQLException e) {
            LOGGER.info(e);
            result = false;
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public boolean deleteByIdLang(long id, String lang) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    //TODO
    public boolean addEntity(Cart cart) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        Map<Book, Integer> cartItems = cart.getCartItems();
        for (Book book : cartItems.keySet()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_CART);) {
                statement.setLong(1, cart.getUserId());
                statement.setLong(2, book.getId());
                statement.setInt(3, cartItems.get(book));
                statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error(e);
                result = false;
            }
        }
        connectionPool.returnConnection(connection);
        return result;
    }
}
