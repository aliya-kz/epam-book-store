package DAO.impl;

import DAO.WishListDao;
import DAO.db_connection.ConnectionPool;
import entity.Book;
import entity.WishList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class WishListDaoImpl implements WishListDao {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static String INSERT_WL = "INSERT into wish_lists (user_id, book_id) values (?,?);";
    private static String DELETE_BOOK = "DELETE from wish_lists WHERE user_id = ? AND book_id = ?;";
    private static String GET_WL = "SELECT book_id from wish_lists WHERE user_id = ?;";

    @Override
    public int addToWishList(int userId, int bookId) {
    Connection connection = connectionPool.takeConnection();
    int result = 0;
    PreparedStatement statement = null;
        try {
        statement = connection.prepareStatement(INSERT_WL);
        statement.setInt(1, userId);
        statement.setInt(2, bookId);
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

    @Override
    public int deleteFromWishList(int userId, int bookId) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_BOOK);
            statement.setInt(1, userId);
            statement.setInt(2, bookId);
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

    @Override
    public WishList getWishList(int userId) {
        WishList wishList = new WishList(userId);
        List<Book> books = new ArrayList<>();
        wishList.setBooks(books);
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(GET_WL);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book (resultSet.getInt("book_id"));
                books.add(book);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return wishList;
    }

    @Override
    public int addEntity(WishList wishList) {
        return 0;
    }

}

