package dao.impl;

import dao.StatusDao;
import dao.db_connection.ConnectionPool;
import entity.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.DaoConstants.*;


public class StatusDaoImpl implements StatusDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String GET_ALL_STATUSES = "SELECT * FROM statuses_lang WHERE lang = ?;";

    @Override
    public List<Status> getAll(String lang) {
        List<Status> statuses = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(GET_ALL_STATUSES);
            statement.setString(1, lang);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Status status = new Status();
                status.setId(resultSet.getLong(STATUS_ID));
                status.setStatusName(resultSet.getString(STATUS_NAME));
                status.setLang(resultSet.getString(LANG));
                statuses.add(status);
            }
        } catch (SQLException e) {
            LOGGER.info(e);
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return statuses;
    }

    @Override
    public boolean deleteById(long id) {

        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }

    @Override
    public boolean deleteByIdLang(long id, String lang){

        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }

    @Override
    public boolean addEntity(Status status){

        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }
}
