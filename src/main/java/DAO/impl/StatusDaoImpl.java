package DAO.impl;

import DAO.StatusDao;
import DAO.db_connection.ConnectionPool;
import entity.Lang;
import entity.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusDaoImpl implements StatusDao {
    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static String GET_ALL_STATUSES = "SELECT * FROM statuses_lang WHERE lang = ?;";
    @Override
    public List<Status> getAll(String lang) {
        List <Status> statuses = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(GET_ALL_STATUSES);
            statement.setString(1, lang);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Status status = new Status();
                status.setId(resultSet.getInt("id"));
                status.setStatusName(resultSet.getString("status_name"));
                status.setLang(resultSet.getString("lang"));
                statuses.add(status);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(e);
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return statuses;
    }

    @Override
    public int addEntity(Status status) {
        return 0;
    }
}
