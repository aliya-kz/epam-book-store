package DAO.impl;

import DAO.AuthorDao;
import DAO.db_connection.ConnectionPool;
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
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static String GET_ALL_AUTHORS = "SELECT al.*, a.image FROM authors_lang al LEFT JOIN authors a ON a.id=al.id;";
    private static String GET_ALL_AUTHORS_LANG = "SELECT al.*, a.image FROM authors_lang al LEFT JOIN authors a ON a.id=al.id WHERE lang = ?;";
    private static String SELECT_MAX = "SELECT max(id) FROM authors;";
    private static String INSERT_AUTHORS = "INSERT into authors (id, image) values (?,?);";
    private static String INSERT_AUTHORS_LANG = "INSERT into authors_lang (id, name, surname, biography, lang) values " +
            "(?, ?, ?, ?, ?);";

    @Override
    public int addEntity(Author author) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        int id = 0;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;

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
            statement.setInt(1, author.getId());
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
         List<Author> sortedList = authors.stream()
                 .sorted(Comparator.comparing(Author::getSurname))
                 .collect(Collectors.toList());
        return sortedList;
    }

    @Override
    public int deleteById(int id) {

        return 0;
    }

    public  static  void main (String[] args) {
 AuthorDao dao =new AuthorDaoImpl();
    }
}
