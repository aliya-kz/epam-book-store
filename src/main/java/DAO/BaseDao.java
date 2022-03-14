package dao;

import dao.db_connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


public interface BaseDao {

    boolean deleteById(long id);

    boolean deleteByIdLang(long id, String lang);

    default boolean setColumnValue(String table, long id, String columnName, Object value) {
        boolean result = true;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        String updateColumn = "UPDATE " + table + " set " + columnName + " = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateColumn)) {
            statement.setObject(1, value);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    default boolean setColumnValueLang(String table, long id, String columnName, Object value, String lang) {
        Logger logger = LogManager.getLogger(this.getClass().getName());
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        String updateColumn = "UPDATE " + table + " set " + columnName + " = ? WHERE id = ? and lang = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateColumn)) {
            statement.setObject(1, value);
            statement.setLong(2, id);
            statement.setString(3, lang);
            statement.executeUpdate();
        } catch (SQLException e) {
            result = false;
            logger.info(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }


    default byte[] getByteImage(int entityId, String table) {
        byte[] image = new byte[0];
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT image FROM " + table + " WHERE id = ?")) {
            statement.setInt(1, entityId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                image = rs.getBytes(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return image;
    }
}
