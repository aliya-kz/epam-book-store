package DAO.impl;

import DAO.BookDao;
import DAO.db_connection.ConnectionPool;
import entity.Author;
import entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.sql.*;
import java.util.*;

public class BookDaoImpl implements BookDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static String INSERT_BOOK = "INSERT INTO books" +
            "(title, description, publisher, quantity, price, category_id, isbn, publ_lang, format_id) " +
            "VALUES " + "(?,?,?,?,?,?,?,?,?);";

    private static String INSERT_AUTHORS_TO_BOOKS = "INSERT INTO authors_to_books (book_id, author_id) values (?,?);";

    private static String UPDATE_BOOK_SQL = "UPDATE books set" +
            "(title, authors, isbn, publisher, quantity, price, category, description, language) " +
            "= " + "(?,?,?,?,?,?,?,?,?) where isbn = ?;";

    private static String SELECT_BOOK_SQL = "select b.*, bl.description,bl.lang from books b left join books_lang bl on b.id=bl.book_id where b.id = ?;";

    @Override
    public int addEntity(Book book) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        try {
            statement = connection.prepareStatement(INSERT_BOOK);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getDescription());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getQuantity());
            statement.setDouble(5, book.getPrice());
            statement.setInt(6, book.getCategoryId());
            statement.setString(7, book.getIsbn());
            statement.setString(8, book.getLanguage());
            statement.setInt(9, book.getFormatId());
            result = statement.executeUpdate();

            statement1 = connection.prepareStatement("SELECT * from books WHERE isbn = ?;");
            ResultSet resultSet = statement1.executeQuery();
            int id = -1;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }

            statement2 = connection.prepareStatement(INSERT_AUTHORS_TO_BOOKS);
            List<Integer> authors = book.getAuthors();
            for (int i = 0; i < authors.size(); i++) {
                statement.setInt(1, book.getId());
                statement.setInt(2, authors.get(i));
            }
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

    @Override
    public List<Book> getAll() {
        Connection connection = connectionPool.takeConnection();
        List<Book> books = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select ab.author_id, b.*, bc.image from authors_to_books ab " +
                    "left join books b on b.id=ab.book_id left join book_covers bc on b.id=bc.id;");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Book book = new Book(id);
                int author_id = resultSet.getInt("author_id");
                int index = books.indexOf(book);
                if (index > -1) {
                    Book existingBook = books.get(index);
                    existingBook.getAuthors().add(author_id);
                } else {
                    book.setTitle(resultSet.getString("title"));
                    List<Integer> authors = new ArrayList<>();
                    authors.add(resultSet.getInt("author_id"));
                    book.setAuthors(authors);
                    book.setPublisher(resultSet.getString("publisher"));
                    book.setIsbn(resultSet.getString("isbn"));
                    book.setQuantity(resultSet.getInt("quantity"));
                    book.setLanguage(resultSet.getString("publ_lang"));
                    book.setPrice(resultSet.getInt("price"));
                    book.setDescription(resultSet.getString("description"));
                    book.setCategoryId(resultSet.getInt("category_id"));
                    book.setFormatId(resultSet.getInt("format_id"));
                    book.setImage(resultSet.getBytes("image"));
                    books.add(book);
                }
            }
        } catch (Exception e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return books;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }

    public int addAuthor(Author author, String lang) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        /*PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        try {
            statement = connection.prepareStatement("INSERT into authors (image) values (?)");
            statement.setString(1, author.getImage()

            result = statement.executeUpdate();

            statement1 = connection.prepareStatement(INSERT_AUTHORS_TO_BOOKS);
            int [] author_ids = book.getAuthors();
            for (int i = 0; i < author_ids.length; i++) {
                statement.setInt(1, book.getId());
                statement.setInt(2, author_ids[i]);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            close(statement);
            close(statement1);
            connectionPool.returnConnection(connection);
        }*/
        return result;
    }

    public List<Book> selectBooksByCategory(int categoryId, String lang) {
        return null;
    }

    public List<Book> findBooksByAuthorIsbnOrTitle(String search) {
        List<Book> books = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
       /* Statement statement = null;
        search = search.toLowerCase();
        try {statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT title, isbn, authors FROM books");
            while (resultSet.next()) {
                String authors = resultSet.getString("authors").toLowerCase();
                String title = resultSet.getString("title").toLowerCase();
                String isbn = resultSet.getString("isbn").toLowerCase();
                if (authors.contains(search) || title.contains(search) || isbn.contains(search)) {
                    Book book = new Book(isbn, title, authors);
                    book.setIsbn(isbn);
                    books.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }*/
        return books;
    }

    @Override
    public Map<String, Object> getParamList(Book book) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", book.getId());
        map.put("title", book.getTitle());
        map.put("authors", book.getAuthors());
        map.put("publisher", book.getPublisher());
        map.put("isbn", book.getIsbn());
        map.put("format", book.getFormatId());
        map.put("language", book.getLanguage());
        map.put("categoryId", book.getCategoryId());
        map.put("price", book.getPrice());
        map.put("quantity", book.getQuantity());
        map.put("description", book.getDescription());
        return map;
    }

    public static void main(String[] args) throws IOException {
        BookDaoImpl impl = new BookDaoImpl();
        impl.addEntity(new Book());
        /*byte[] result = impl.getByteImage(1, "authors");
        ByteArrayInputStream bis = new ByteArrayInputStream(result);
        BufferedImage bImage2 = null;
        try {
            bImage2 = ImageIO.read(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean jpg = false;
        try {
            jpg = ImageIO.write(bImage2, "jpeg", new File("D:/test8.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
