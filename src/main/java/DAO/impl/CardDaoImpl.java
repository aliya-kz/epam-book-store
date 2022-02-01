package DAO.impl;

import DAO.CardDao;
import DAO.db_connection.ConnectionPool;
import entity.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class CardDaoImpl implements CardDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static String INSERT_CARD = "INSERT INTO cards (user_id, card_number) VALUES (?,?);";
    private static String DELETE_CARD = "DELETE from cards WHERE card_id = ?;";

    @Override
    public int addEntity(Card card) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_CARD);
            statement.setInt(1, card.getUserId());
            statement.setString(2, card.getCardNumber());
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
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_CARD);
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
}
