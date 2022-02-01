package DAO.impl;

import DAO.LanguageDao;
import DAO.db_connection.ConnectionPool;
import entity.Lang;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LanguageDaoImpl implements LanguageDao {

   private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String INSERT_LANG = "INSERT into lang (title) values (?);";
    private static final String GET_ALL_LANG = "SELECT * from lang;";

    @Override
    public int addEntity(Lang lang) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_LANG);
            statement.setString(1,lang.getTitle());
            result = statement.executeUpdate();
            close(statement);
        } catch (Exception e) {
            //  LOGGER.info(e);
            e.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public List <Lang> getAll() {
        List <Lang> langs = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(GET_ALL_LANG);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Lang lang = new Lang();
                lang.setId(resultSet.getInt("id"));
                lang.setTitle(resultSet.getString("lang"));
                langs.add(lang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return langs;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }
}