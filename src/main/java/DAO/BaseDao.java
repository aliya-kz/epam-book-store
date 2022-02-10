package DAO;

import DAO.db_connection.ConnectionPool;
import entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;


public interface BaseDao <T extends Entity> {

    int deleteById (int id);

    int deleteByIdLang(int id, String lang);

    int addEntity(T t);

    default int setColumnValue(String table, int id, String columnName, Object value) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        String updateColumn = "UPDATE " + table + " set " + columnName + " = ? WHERE id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(updateColumn);
            statement.setObject(1, value);
            statement.setInt(2, id);
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    default int setColumnValueLang(String table, int id, String columnName, Object value, String lang) {
        Logger logger = LogManager.getLogger(this.getClass().getName());
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        String updateColumn = "UPDATE " + table + " set " + columnName + " = ? WHERE id = ? and lang = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(updateColumn);
            statement.setObject(1, value);
            statement.setInt(2, id);
            statement.setString(3, lang);
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    default void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    default byte[] getByteImage(int entityId, String table) {
        byte[] image = new byte[0];
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT image FROM " + table + " WHERE id = ?");
            statement.setInt(1, entityId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                image = rs.getBytes(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return image;
    }

    default int updateByteImage (String table, int id, String url) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try {
            File file = new File(url);
            FileInputStream fis = new FileInputStream(file);
            statement = connection.prepareStatement("UPDATE " + table + " SET image = ? where id = ?;");
            statement.setInt(2, id);
            statement.setBinaryStream(1, fis, file.length());
            result = statement.executeUpdate();
            fis.close();
            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }
}
