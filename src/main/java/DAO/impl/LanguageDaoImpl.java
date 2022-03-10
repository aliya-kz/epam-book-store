package dao.impl;

import dao.LanguageDao;
import dao.db_connection.ConnectionPool;
import entity.Lang;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.DaoConstants.*;


public class LanguageDaoImpl implements LanguageDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String INSERT_LANG = "INSERT into langs (title) values (?);";

    private static final String GET_ALL_LANG = "SELECT * from langs;";

    @Override
    public boolean addEntity(Lang lang) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_LANG);
            statement.setString(1, lang.getTitle());
            statement.executeUpdate();
            close(statement);
        } catch (SQLException e) {
            LOGGER.info(e);
            result = false;
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public List<Lang> getAll() {
        List<Lang> langs = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(GET_ALL_LANG);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lang lang = new Lang();
                lang.setId(resultSet.getInt(ID));
                lang.setTitle(resultSet.getString(TITLE));
                langs.add(lang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.info(e);
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return langs;
    }

    @Override
    public boolean deleteById(long id) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean deleteByIdLang(long id, String lang) {

        throw new UnsupportedOperationException("Method not supported");
    }
}
