package dao.impl;

import dao.AuthorDao;
import dao.db_connection.ConnectionPool;
import entity.Author;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorDaoImpl implements AuthorDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String GET_ALL_AUTHORS_LANG = "SELECT al.*, a.image FROM authors_lang al LEFT JOIN authors a ON a.id=al.id WHERE lang = ?;";
    private final static String SELECT_MAX = "SELECT max(id) FROM authors;";
    private final static String INSERT_AUTHORS = "INSERT into authors (id, image) values (?,?);";
    private final static String INSERT_AUTHORS_LANG = "INSERT into authors_lang (id, name, surname, biography, lang) values " +
            "(?, ?, ?, ?, ?);";
    private final static String DELETE_AUTHORS_LANG ="DELETE from authors_lang WHERE id = ? and lang = ?;";
    private final static String DELETE_AUTHORS ="DELETE from authors WHERE id = ?;";
    private final static String SELECT_ALL ="SELECT id, name, surname FROM authors_lang;";

    public int addEntity(Author author) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        int id = 0;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        try {
            statement = connection.prepareStatement(SELECT_MAX);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1) + 1;
            }
            author.setId(id);
            statement1 = connection.prepareStatement(INSERT_AUTHORS);
            statement1.setInt(1, id);
            statement1.setBytes(2, author.getImage());
            statement1.executeUpdate();
            result = addTranslation(author);
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            close(statement);
            close(statement1);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public int addTranslation (Author author) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_AUTHORS_LANG);
            statement.setLong(1, author.getId());
            statement.setString(2, author.getName());
            statement.setString(3, author.getSurname());
            statement.setString(4, author.getBiography());
            statement.setString(5, author.getLang());
            result = statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

     public List<Author> getAll(String lang) {
        List <Author> authors = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(GET_ALL_AUTHORS_LANG);
            statement.setString(1, lang);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getInt("id"));
                author.setName(resultSet.getString("name"));
                author.setSurname(resultSet.getString("surname"));
                author.setBiography(resultSet.getString("biography"));
                author.setImage(resultSet.getBytes("image"));
                author.setFullName(resultSet.getString("surname") + " " + resultSet.getString("name"));
                author.setLang(lang);
                authors.add(author);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
         return authors.stream()
                 .sorted(Comparator.comparing(Author::getSurname))
                 .collect(Collectors.toList());
    }

    @Override
    public int deleteById(long id) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_AUTHORS);
            statement.setLong(1, id);
            result = statement.executeUpdate();
            close(statement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public int deleteByIdLang(long id, String lang) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_AUTHORS_LANG);
            statement.setLong(1, id);
            statement.setString(2, lang);
            result = statement.executeUpdate();
            close(statement);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public List <Integer> searchAuthors(String search) {
        List <Integer> authors = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String surname = resultSet.getString("surname");
                String name = resultSet.getString("name");
                if (surname.equalsIgnoreCase(search) || name.equalsIgnoreCase(search)) {
                 authors.add(resultSet.getInt("id"));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return authors;
    }
}
