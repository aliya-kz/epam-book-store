package DAO.impl;

import DAO.CartDao;
import DAO.db_connection.ConnectionPool;
import entity.Book;
import entity.Cart;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int addEntity(Cart cart) {
        return 0;
    }


    @Override
    public Cart getCart(int userId) {
        Cart cart = new Cart (userId);
        Map<Book, Integer> cartItems = new HashMap<>();
        cart.setCartItems(cartItems);
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_CART);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book=new Book (resultSet.getInt("book_id"));
                cartItems.put(book, resultSet.getInt("quantity"));
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

    public int deleteById(int bookId, int userId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_FROM_CART);
            statement.setInt(1, bookId);
            statement.setInt(2, userId);
            result = statement.executeUpdate();
            close(statement);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public int deleteCart(int userId) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_CART);
            statement.setInt(1, userId);
            result = statement.executeUpdate();
            close(statement);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }


    public int addToCart(int userId, int bookId, int quantity) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        try {
            statement = connection.prepareStatement(SELECT_BOOK);
            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int oldQuantity = resultSet.getInt("quantity");

                statement1 = connection.prepareStatement(UPDATE_QTY);
                statement1.setInt(1, oldQuantity + quantity);
                statement1.setInt(2, bookId);
                statement1.setInt(3, userId);
                result = statement1.executeUpdate();
            } else {
                statement2 = connection.prepareStatement(INSERT_CART);
                statement2.setInt(1, userId);
                statement2.setInt(2, bookId);
                statement2.setInt(3, quantity);
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
    public int deleteFromCart(int cartId, String isbn) {
        return 0;
    }

    @Override
    public int getCartCost(int cartId) {
        return 0;
    }

    @Override
    public int updateQuantity(int bookId, int userId, int quantity) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_QTY);
            statement.setInt(1, quantity);
            statement.setInt(2, bookId);
            statement.setInt(3, userId);
            result = statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public static void main (String [] args) {
        CartDao dao= new CartDaoImpl();
        Cart cart = dao.getCart(4);
        System.out.println( "cart " + cart);
        System.out.println(cart.getCartItems());
    }
}
