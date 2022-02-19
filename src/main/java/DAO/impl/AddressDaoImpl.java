package DAO.impl;

import DAO.AddressDao;
import DAO.db_connection.ConnectionPool;
import entity.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddressDaoImpl implements AddressDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String INSERT_ADDRESS = "INSERT INTO addresses (user_id, address) VALUES (?,?);";
    private final static String DELETE_ADDRESS = "UPDATE addresses set user_id = 1 where address_id = ?;";

    @Override
    public int addEntity(Address address) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_ADDRESS);
            statement.setInt(1, address.getUserId());
            statement.setString(2, address.getAddress());
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
    public int deleteById(int id) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_ADDRESS);
            statement.setInt(1, id);
            result = statement.executeUpdate();
            close(statement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public int deleteByIdLang(int id, String lang) {
        return 0;
    }
}
