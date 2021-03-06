package dao.impl;

import dao.CardDao;
import dao.db_connection.ConnectionPool;
import entity.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static dao.DaoConstants.UNSUPPORTED_MESSAGE;


public class CardDaoImpl implements CardDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String INSERT_CARD = "INSERT INTO cards (user_id, card_number, is_active) VALUES (?,?,?);";
    private final static String DELETE_CARD = "UPDATE cards set is_active = false WHERE card_id = ?;";

    @Override
    public boolean addEntity(Card card) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CARD)) {
            statement.setLong(1, card.getUserId());
            statement.setString(2, card.getCardNumber());
            statement.setBoolean(3, true);
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
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CARD)) {
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
