package dao.impl;

import dao.AuthorDao;
import dao.db_connection.ConnectionPool;
import entity.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static dao.DaoConstants.*;


public class AuthorDaoImpl implements AuthorDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String GET_ALL_AUTHORS_LANG = "SELECT al.*, a.image FROM authors_lang al LEFT JOIN authors a ON a.id=al.id WHERE lang = ?;";
    private final static String INSERT_AUTHORS = "INSERT into authors (image) values (?);";
    private final static String INSERT_AUTHORS_LANG = "INSERT into authors_lang (id, name, surname, biography, lang) values " +
            "(?, ?, ?, ?, ?);";
    private final static String DELETE_AUTHORS_LANG = "DELETE from authors_lang WHERE id = ? and lang = ?;";
    private final static String DELETE_AUTHORS = "DELETE from authors WHERE id = ?;";
    private final static String SELECT_ALL = "SELECT id, name, surname FROM authors_lang;";
    private final static String SELECT_ID = "SELECT id FROM authors where id = ?;";
    private final static String CHECK_IF_EXISTS = "SELECT * FROM authors_lang where id = ? and lang = ?;";

    public boolean addEntity(Author author) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement insertAuthorImage = connection.prepareStatement(INSERT_AUTHORS, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertAuthorDetails = connection.prepareStatement(INSERT_AUTHORS_LANG);) {
            connection.setAutoCommit(false);
            long id = 0;
            insertAuthorImage.setBytes(1, author.getImage());
            insertAuthorImage.executeUpdate();
            ResultSet rs = insertAuthorImage.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(ID);
            }
            insertAuthorDetails.setLong(1, id);
            insertAuthorDetails.setString(2, author.getName());
            insertAuthorDetails.setString(3, author.getSurname());
            insertAuthorDetails.setString(4, author.getBiography());
            insertAuthorDetails.setString(5, author.getLang());
            insertAuthorDetails.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    LOGGER.info(e);
                    LOGGER.info(ROLLED_BACK_MESSAGE);
                    connection.rollback();
                } catch (SQLException excep) {
                    LOGGER.warn(excep);
                }
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

    public boolean addTranslation(Author author) {
        if (authorIdExists(author.getId())) {
            return false;
        }
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_AUTHORS_LANG);) {
            statement.setLong(1, author.getId());
            statement.setString(2, author.getName());
            statement.setString(3, author.getSurname());
            statement.setString(4, author.getBiography());
            statement.setString(5, author.getLang());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn(e);
            result = false;
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public boolean authorIdExists(long id) {
        Connection connection = connectionPool.takeConnection();
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ID);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public boolean authorWithIdAndLangExists(Author author) {
        Connection connection = connectionPool.takeConnection();
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(CHECK_IF_EXISTS);) {
            statement.setLong(1, author.getId());
            statement.setString(2, author.getLang());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public List<Author> getAll(String lang) {
        List<Author> authors = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_AUTHORS_LANG);) {
            statement.setString(1, lang);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getInt(ID));
                author.setName(resultSet.getString(NAME));
                author.setSurname(resultSet.getString(SURNAME));
                author.setBiography(resultSet.getString(BIOGRAPHY));
                author.setImage(resultSet.getBytes(IMAGE));
                author.setFullName(resultSet.getString(SURNAME) + " " + resultSet.getString(NAME));
                author.setLang(lang);
                authors.add(author);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return authors.stream()
                .sorted(Comparator.comparing(Author::getSurname))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(long id) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_AUTHORS);) {
            statement.setLong(1, id);
            statement.executeUpdate();
            close(statement);
        } catch (SQLException e) {
            LOGGER.warn(e);
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
        try (PreparedStatement statement = connection.prepareStatement(DELETE_AUTHORS_LANG);) {
            statement.setLong(1, id);
            statement.setString(2, lang);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public List<Integer> searchAuthors(String search) {
        List<Integer> authors = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String surname = resultSet.getString(SURNAME);
                String name = resultSet.getString(NAME);
                if (surname.equalsIgnoreCase(search) || name.equalsIgnoreCase(search)) {
                    authors.add(resultSet.getInt(ID));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return authors;
    }
}
