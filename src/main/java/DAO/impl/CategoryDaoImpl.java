package dao.impl;

import dao.CategoryDao;
import dao.db_connection.ConnectionPool;
import entity.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static dao.DaoConstants.*;


public class CategoryDaoImpl implements CategoryDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String INSERT_CATEGORIES = "INSERT into categories (id) values (?);";
    private final static String INSERT_CATEGORIES_LANG = "INSERT into categories_lang (id, category_name, lang) values (?,?,?);";
    private final static String SELECT_ALL_CATEGORIES_LANG = "SELECT * FROM categories_lang WHERE lang = ?;";
    private final static String DELETE_CATEGORIES_LANG = "DELETE from categories_lang WHERE id = ?;";
    private final static String DELETE_CATEGORIES = "DELETE from categories WHERE id = ?;";
    private final static String SELECT_CATEGORY_LANG = "SELECT * from categories_lang where id = ? and lang = ?;";
    private final static String SELECT_CATEGORY = "SELECT * from categories where id = ?;";

    public List<Category> getAll(String lang) {
        List<Category> categories = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_ALL_CATEGORIES_LANG);
            statement.setString(1, lang);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt(ID));
                category.setCategoryName(resultSet.getString(CATEGORY_NAME));
                category.setLang(resultSet.getString(LANG));
                categories.add(category);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            ex.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return categories.stream()
                .sorted(Comparator.comparing(Category::getCategoryName))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addEntity(Category category) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        try {
            statement = connection.prepareStatement(SELECT_CATEGORY_LANG);
            statement.setLong(1, category.getId());
            statement.setString(2, category.getLang());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                statement1 = connection.prepareStatement(SELECT_CATEGORY);
                statement1.setLong(1, category.getId());
                ResultSet resultSet1 = statement1.executeQuery();
                if (!resultSet1.next()) {
                    statement2 = connection.prepareStatement(INSERT_CATEGORIES);
                    statement2.setLong(1, category.getId());
                    statement2.executeUpdate();
                }
                statement3 = connection.prepareStatement(INSERT_CATEGORIES_LANG);
                statement3.setLong(1, category.getId());
                statement3.setString(2, category.getCategoryName());
                statement3.setString(3, category.getLang());
                statement3.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            result = false;
        } finally {
            close(statement);
            close(statement1);
            close(statement2);
            close(statement3);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public boolean deleteById(long id) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_CATEGORIES);
            statement.setLong(1, id);
            statement.executeUpdate();
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
    public boolean deleteByIdLang(long id, String lang) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_CATEGORIES_LANG);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(e);
            result = false;
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }
}


