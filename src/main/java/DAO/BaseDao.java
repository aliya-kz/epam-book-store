package DAO;

import DAO.db_connection.ConnectionPool;
import entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public interface BaseDao <T extends Entity> {

    default int deleteById(String table, int id) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        try {
            String request = "DELETE from " + table + " WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(request);
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

    default int deleteByIdLang(String table, int id, String lang) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        try {
            String request = "DELETE from " + table + " WHERE id = ? and lang = ?;";
            PreparedStatement statement = connection.prepareStatement(request);
            statement.setInt(1, id);
            statement.setString(2,lang);
            result = statement.executeUpdate();
            close(statement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    int addEntity(T t);

    default Object getColumnValue(String table, int id, String columnName) {
        Object value = new Object();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        String sqlRequest = "SELECT " + columnName + " from " + table + " WHERE id = ?;";
        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                value = resultSet.getObject(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return value;
    }

    default Object getColumnValueLang(String table, int id, String columnName, String lang) {
        Object value = new Object();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        String sqlRequest = "SELECT " + columnName + " from " + table + " WHERE id = ? and lang = ?;";
        try {
            statement = connection.prepareStatement(sqlRequest);
            statement.setInt(1, id);
            statement.setString(2, lang);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                value = resultSet.getObject(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return value;
    }

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
