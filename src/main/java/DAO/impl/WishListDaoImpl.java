package dao.impl;

import dao.WishListDao;
import dao.db_connection.ConnectionPool;
import entity.Book;
import entity.WishList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.DaoConstants.*;


public class WishListDaoImpl implements WishListDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String INSERT_WL = "INSERT into wish_lists (user_id, book_id) values (?,?);";
    private final static String DELETE_BOOK = "DELETE from wish_lists WHERE user_id = ? AND book_id = ?;";
    private final static String GET_WL = "SELECT book_id from wish_lists WHERE user_id = ?;";

    @Override
    public boolean addToWishList(long userId, long bookId) {
    Connection connection = connectionPool.takeConnection();
    boolean result = true;
    try (PreparedStatement statement = connection.prepareStatement(INSERT_WL);) {
        statement.setLong(1, userId);
        statement.setLong(2, bookId);
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
    public boolean deleteFromTable(long userId, long bookId) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_BOOK);) {
            statement.setLong(1, userId);
            statement.setLong(2, bookId);
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
    public WishList getWishList(long userId) {
        WishList wishList = new WishList();
        wishList.setUserId(userId);
        List<Book> books = new ArrayList<>();
        wishList.setBooks(books);
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_WL);) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book (resultSet.getLong(BOOK_ID));
                books.add(book);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return wishList;
    }

    @Override
    public boolean deleteById(long id) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean deleteByIdLang(long id, String lang) {

        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean addEntity(WishList wishList) {

        throw new UnsupportedOperationException("Method not supported");
    }

}

