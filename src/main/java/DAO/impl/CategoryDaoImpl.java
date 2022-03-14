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
    private final static String SELECT_CATEGORY = "SELECT * from categories where id = ?;";

    public List<Category> getAll(String lang) {
        List<Category> categories = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CATEGORIES_LANG)) {
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
            connectionPool.returnConnection(connection);
        }
        return categories.stream()
                .sorted(Comparator.comparingLong(Category::getId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public boolean addEntity(Category category) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement checkIfIdExists = connection.prepareStatement(SELECT_CATEGORY);
             PreparedStatement insertCategory = connection.prepareStatement(INSERT_CATEGORIES);
             PreparedStatement insertCategoryLang = connection.prepareStatement(INSERT_CATEGORIES_LANG)) {
            connection.setAutoCommit(false);

            checkIfIdExists.setLong(1, category.getId());
            ResultSet resultSet = checkIfIdExists.executeQuery();
            if (!resultSet.next()) {
                insertCategory.setLong(1, category.getId());
                insertCategory.executeUpdate();
            }

            insertCategoryLang.setLong(1, category.getId());
            insertCategoryLang.setString(2, category.getCategoryName());
            insertCategoryLang.setString(3, category.getLang());
            insertCategoryLang.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            result = false;
            try {
                LOGGER.warn(ROLLED_BACK_MESSAGE);
                connection.rollback();
            } catch (SQLException excep) {
                LOGGER.warn(excep);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.info(e);
            }
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public boolean deleteById(long id) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORIES)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(e);
            result = false;
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public boolean deleteByIdLang(long id, String lang) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORIES_LANG)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(e);
            result = false;
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }
}


