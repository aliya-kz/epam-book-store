package dao.impl;

import dao.BookDao;
import dao.db_connection.ConnectionPool;
import entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static dao.DaoConstants.*;


public class BookDaoImpl implements BookDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String INSERT_BOOK = "INSERT INTO books" +
            "(title, description, publisher, quantity, price, category_id, isbn, publ_lang, format_id) " +
            "VALUES " + "(?,?,?,?,?,?,?,?,?);";

    private final static String INSERT_AUTHORS_TO_BOOKS = "INSERT INTO authors_to_books (book_id, author_id) values (?,?);";

    private final static String DELETE_AUTHORS_TO_BOOKS = "DELETE from authors_to_books WHERE book_id = ?;";

    private final static String INSERT_COVER = "INSERT into book_covers (id, image) values (?, ?)";

    private final static String GET_QUANTITY = "SELECT quantity from books where id = ?;";

    private final static String UPDATE_QUANTITY = "UPDATE books set quantity = ? where id = ?;";

    private final static String SELECT_ALL_BOOKS = "SELECT b.*, ab.author_id, cl.category_name, fl.format_name, bc.image from books b " +
            "left join authors_to_books ab on b.id=ab.book_id left join categories_lang cl on cl.id=b.category_id left join " +
            "formats_lang fl on fl.id=b.format_id left join book_covers bc on bc.id=b.id WHERE fl.lang= ? and cl.lang=?;";

    @Override
    public boolean addEntity(Book book) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement insertBook = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertAuthors = connection.prepareStatement(INSERT_AUTHORS_TO_BOOKS);
             PreparedStatement insertCover = connection.prepareStatement(INSERT_COVER);) {
            connection.setAutoCommit(false);
            long id = 0;
            insertBook.setString(1, book.getTitle());
            insertBook.setString(2, book.getDescription());
            insertBook.setString(3, book.getPublisher());
            insertBook.setInt(4, book.getQuantity());
            insertBook.setDouble(5, book.getPrice());
            insertBook.setLong(6, book.getCategoryId());
            insertBook.setString(7, book.getIsbn());
            insertBook.setString(8, book.getLanguage());
            insertBook.setLong(9, book.getFormatId());
            insertBook.executeUpdate();
            ResultSet rs = insertBook.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(ID);
            }

            List<Long> authors = book.getAuthors();
            for (int i = 0; i < authors.size(); i++) {
                insertAuthors.setLong(1, id);
                insertAuthors.setLong(2, authors.get(i));
                insertAuthors.executeUpdate();
            }

            insertCover.setLong(1, id);
            insertCover.setBytes(2, book.getImage());
            insertCover.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            result = false;
            if (connection != null) {
                try {
                    LOGGER.warn(ROLLED_BACK_MESSAGE);
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

    @Override
    public List<Book> getAll(String lang) {
        Connection connection = connectionPool.takeConnection();
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BOOKS);) {
            statement.setString(1, lang);
            statement.setString(2, lang);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(ID);
                Book book = new Book(id);
                long authorId = resultSet.getLong(AUTHOR_ID);
                int index = books.indexOf(book);
                if (index > -1) {
                    Book existingBook = books.get(index);
                    existingBook.getAuthors().add(authorId);
                } else {
                    book.setTitle(resultSet.getString(TITLE));
                    List<Long> authors = new ArrayList<>();
                    authors.add(resultSet.getLong(AUTHOR_ID));
                    book.setAuthors(authors);
                    book.setPublisher(resultSet.getString(PUBLISHER));
                    book.setIsbn(resultSet.getString(ISBN));
                    book.setQuantity(resultSet.getInt(QUANTITY));
                    book.setLanguage(resultSet.getString(PUBLICATION_LANGUAGE));
                    book.setPrice(resultSet.getInt(PRICE));
                    book.setDescription(resultSet.getString(DESCRIPTION));
                    book.setCategoryId(resultSet.getInt(CATEGORY_ID));
                    book.setCategory(resultSet.getString(CATEGORY_NAME));
                    book.setFormatId(resultSet.getInt(FORMAT_ID));
                    book.setFormat(resultSet.getString(FORMAT_NAME));
                    book.setImage(resultSet.getBytes(IMAGE));
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return books.stream()
                .sorted(Comparator.comparingLong(Book::getId))
                .collect(Collectors.toList());
    }

    public boolean deleteBookAuthors(long bookId) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(DELETE_AUTHORS_TO_BOOKS);) {
            statement.setLong(1, bookId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            result = false;
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public int setBookAuthors(long bookId, List<Integer> authorIds) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_AUTHORS_TO_BOOKS);) {
            for (int author : authorIds) {
                statement.setLong(1, bookId);
                statement.setInt(2, author);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public boolean deleteById(long id) {
        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }

    @Override
    public boolean purchaseBooks(Map<Book, Integer> cartItems) {
        boolean result = true;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement getQuantity = connection.prepareStatement(GET_QUANTITY);
             PreparedStatement updateQuantity = connection.prepareStatement(UPDATE_QUANTITY);) {
            connection.setAutoCommit(false);
            for (Book book : cartItems.keySet()) {
                long bookId = book.getId();
                int quantity = cartItems.get(book);
                getQuantity.setLong(1, bookId);
                ResultSet resultSet = getQuantity.executeQuery();
                if (resultSet.next()) {
                    int sqlQuantity = resultSet.getInt(QUANTITY);
                    if (sqlQuantity >= quantity) {
                        int newQuantity = sqlQuantity - quantity;
                        updateQuantity.setInt(1, newQuantity);
                        updateQuantity.setLong(2, bookId);
                        updateQuantity.addBatch();
                    } else {
                        return false;
                    }
                }
            }
            updateQuantity.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
            result = false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.warn(e);
            }
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public boolean deleteByIdLang(long id, String lang) {
        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }

    public List<Book> filterByCategory(List<Book> books, long[] categoryIds) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            for (long categoryId : categoryIds) {
                if (book.getCategoryId() == categoryId) {
                    result.add(book);
                }
            }
        }
        return result;
    }

    public List<Book> filterByFormat(List<Book> books, long[] formatIds) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            for (long formatId : formatIds) {
                if (book.getFormatId() == formatId) {
                    result.add(book);
                }
            }
        }
        return result;
    }

    public List<Book> filterByPublLang(List<Book> books, String[] publLangs) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            for (String lang : publLangs) {
                if (book.getLanguage().equals(lang)) {
                    result.add(book);
                    continue;
                }
            }
        }
        return result;
    }

    public boolean setByteImage(long id, String url) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement statement = connection.prepareStatement(INSERT_COVER);) {
            File file = new File(url);
            FileInputStream fis = new FileInputStream(file);
            statement.setLong(1, id);
            statement.setBinaryStream(2, fis, file.length());
            statement.executeUpdate();
            fis.close();
        } catch (SQLException | IOException e) {
            result = false;
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }
}


