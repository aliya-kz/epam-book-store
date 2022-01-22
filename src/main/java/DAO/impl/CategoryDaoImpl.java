package DAO.impl;

import DAO.CategoryDao;
import DAO.db_connection.ConnectionPool;
import entity.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static String INSERT_CATEGORIES = "INSERT into categories (id) values (?);";
    private static String INSERT_CATEGORIES_LANG = "INSERT into categories_lang (id, category_name, lang) values (?,?,?);";
    private static String SELECT_ALL_CATEGORIES = "SELECT * FROM categories_lang;";
    private static String SELECT_ALL_CATEGORIES_LANG = "SELECT * FROM categories_lang WHERE lang = ?;";
    private static String DELETE_CATEGORIES_LANG = "DELETE from categories_lang WHERE id = ?;";
    private static String DELETE_CATEGORIES = "DELETE from categories WHERE id = ?;";
    private static String SELECT_CATEGORY_BY_ID = "SELECT * from categories where id = ?;";

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_ALL_CATEGORIES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("category_name"));
                category.setLang(resultSet.getString("lang"));
                categories.add(category);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            ex.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        Collections.sort(categories);
        return categories;
    }

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
                category.setId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("category_name"));
                category.setLang(resultSet.getString("lang"));
                categories.add(category);
            }
        } catch (SQLException ex) {
            LOGGER.error(ex);
            ex.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        Collections.sort(categories);
        return categories;
    }

    @Override
    public int addEntity(Category category) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        try {
            statement = connection.prepareStatement(SELECT_CATEGORY_BY_ID);
            statement.setInt(1, category.getId());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                statement1 = connection.prepareStatement(INSERT_CATEGORIES);
                statement1.setInt(1, category.getId());
                result = statement1.executeUpdate();
            }

            statement2 = connection.prepareStatement(INSERT_CATEGORIES_LANG);
            statement2.setInt(1, category.getId());
            statement2.setString(2, category.getCategoryName());
            statement2.setString(3, category.getLang());
            result = statement2.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            close(statement);
            close(statement1);
            close(statement2);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public int deleteById(int id) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        try {
            statement = connection.prepareStatement(DELETE_CATEGORIES_LANG);
            statement.setInt(1, id);
            result = statement.executeUpdate();

            statement1 = connection.prepareStatement(DELETE_CATEGORIES);
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(e);
        } finally {
            close(statement);
            close(statement1);
            connectionPool.returnConnection(connection);
        }
        return result;
    }
}


