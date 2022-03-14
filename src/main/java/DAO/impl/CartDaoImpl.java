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
    private final static String DELETE_CART = "DELETE from carts WHERE user_id = ?;";
    private final static int MAX_BOOKS_QUANTITY = 8;
    private final static String DELETE_CART_ITEM = "DELETE from carts WHERE id = ?;";


    @Override
    public Cart getCart(long userId) {
        Cart cart = new Cart(userId);
        Map<Book, Integer> cartItems = new HashMap<>();
        cart.setCartItems(cartItems);
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CART)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book(resultSet.getInt(BOOK_ID));
                book.setCartItemId(resultSet.getLong(ID));
                cartItems.put(book, resultSet.getInt(QUANTITY));
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return cart;
    }

    public boolean deleteById(long id) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CART_ITEM)) {
            statement.setLong(1, id);
            statement.executeUpdate();
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
        try (PreparedStatement checkIfExists = connection.prepareStatement(SELECT_BOOK);
             PreparedStatement updateQuantity = connection.prepareStatement(UPDATE_QTY);
             PreparedStatement addNew = connection.prepareStatement(INSERT_CART)) {
            connection.setAutoCommit(false);
            checkIfExists.setLong(1, userId);
            checkIfExists.setLong(2, bookId);
            ResultSet resultSet = checkIfExists.executeQuery();
            if (resultSet.next()) {
                int sqlQuantity = resultSet.getInt(QUANTITY);
                if (sqlQuantity + quantity > MAX_BOOKS_QUANTITY) {
                    updateQuantity.setInt(1, MAX_BOOKS_QUANTITY);
                } else {
                    updateQuantity.setInt(1, sqlQuantity + quantity);
                }
                updateQuantity.setLong(2, bookId);
                updateQuantity.setLong(3, userId);
                updateQuantity.executeUpdate();
            } else {
                addNew.setLong(1, userId);
                addNew.setLong(2, bookId);
                addNew.setInt(3, quantity);
                addNew.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                LOGGER.warn(ROLLED_BACK_MESSAGE);
                result = false;
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
    public boolean updateQuantity(long bookId, long userId, int quantity) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QTY)) {
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
    public boolean deleteCart(long userId) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CART)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
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
        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }

    @Override
    public boolean addEntity(Cart cart) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        Map<Book, Integer> cartItems = cart.getCartItems();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CART)) {
            connection.setAutoCommit(false);
            for (Book book : cartItems.keySet()) {
                statement.setLong(1, cart.getUserId());
                statement.setLong(2, book.getId());
                statement.setInt(3, cartItems.get(book));
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            result = false;
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
}
