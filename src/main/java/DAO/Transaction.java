package dao;

import dao.db_connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Transaction {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static String GET_ALL_STATUSES = "SELECT * FROM statuses_lang WHERE lang = ?;";

    public void execute (List<String> sqlRequests) throws SQLException {
    Connection connection;
        Statement statement = null;
        try {
            connection = connectionPool.takeConnection();
            try {
                connection.setAutoCommit(false);
                statement = connection.createStatement();
                statement.executeUpdate("hhh");
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        LOGGER.warn(e);
                    }
                    try {
                        if (connection != null) {
                            connection.setAutoCommit(true);
                            connection.close();
                        }
                    } catch (SQLException e) {
                        LOGGER.warn(e);
                    }
                }
            }
        } finally {

        }
    }
}
