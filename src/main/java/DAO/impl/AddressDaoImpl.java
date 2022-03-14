package dao.impl;

import dao.AddressDao;
import dao.db_connection.ConnectionPool;
import entity.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static dao.DaoConstants.UNSUPPORTED_MESSAGE;


public class AddressDaoImpl implements AddressDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String INSERT_ADDRESS = "INSERT INTO addresses (user_id, address, is_active) VALUES (?,?,?);";
    private final static String DELETE_ADDRESS = "UPDATE addresses set is_active = false where address_id = ?;";

    @Override
    public boolean addEntity(Address address) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ADDRESS)) {
            statement.setLong(1, address.getUserId());
            statement.setString(2, address.getAddress());
            statement.setBoolean(3, true);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
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
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ADDRESS)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
}

