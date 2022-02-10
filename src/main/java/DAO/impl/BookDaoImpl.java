package DAO.impl;

import DAO.BookDao;
import DAO.db_connection.ConnectionPool;
import entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class BookDaoImpl implements BookDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String INSERT_BOOK = "INSERT INTO books" +
            "(title, description, publisher, quantity, price, category_id, isbn, publ_lang, format_id) " +
            "VALUES " + "(?,?,?,?,?,?,?,?,?);";

    private final static String INSERT_AUTHORS_TO_BOOKS = "INSERT INTO authors_to_books (book_id, author_id) values (?,?);";

    private final static String SELECT_ID = "SELECT id from books WHERE isbn = ?;";

    private final static String DELETE_AUTHORS_TO_BOOKS = "DELETE from authors_to_books WHERE book_id = ?;";

    private final static String INSERT_COVER = "INSERT into book_covers (id, image) values (?, ?)";

       private final static String SELECT_ALL_BOOKS = "SELECT b.*, ab.author_id, cl.category_name, fl.format_name, bc.image from books b " +
            "left join authors_to_books ab on b.id=ab.book_id left join categories_lang cl on cl.id=b.category_id left join " +
            "formats_lang fl on fl.id=b.format_id left join book_covers bc on bc.id=b.id WHERE fl.lang= ? and cl.lang=?;";

    @Override
    public int addEntity(Book book) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
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

            statement1 = connection.prepareStatement(SELECT_ID);
            statement1.setString(1, book.getIsbn());
            ResultSet resultSet = statement1.executeQuery();
            int id = -1;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }

            statement2 = connection.prepareStatement(INSERT_AUTHORS_TO_BOOKS);
            List<Integer> authors = book.getAuthors();
            for (int i = 0; i < authors.size(); i++) {
                statement2.setInt(1, id);
                statement2.setInt(2, authors.get(i));
                statement2.executeUpdate();
            }

            statement3 = connection.prepareStatement(INSERT_COVER);
            statement3.setInt(1, id);
            statement3.setBytes(2, book.getImage());
            result = statement3.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
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
    public List<Book> getAll(String lang) {
        Connection connection = connectionPool.takeConnection();
        List<Book> books = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SELECT_ALL_BOOKS);
            statement.setString(1, lang);
            statement.setString(2, lang);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Book book = new Book(id);
                int authorId = resultSet.getInt("author_id");
                int index = books.indexOf(book);
                if (index > -1) {
                    Book existingBook = books.get(index);
                    existingBook.getAuthors().add(authorId);
                } else {
                    book.setTitle(resultSet.getString("title"));
                    List<Integer> authors = new ArrayList<>();
                    if (!authors.contains(resultSet.getInt("author_id"))) {
                        authors.add(resultSet.getInt("author_id"));
                    }
                    book.setAuthors(authors);
                    book.setPublisher(resultSet.getString("publisher"));
                    book.setIsbn(resultSet.getString("isbn"));
                    book.setQuantity(resultSet.getInt("quantity"));
                    book.setLanguage(resultSet.getString("publ_lang"));
                    book.setPrice(resultSet.getInt("price"));
                    book.setDescription(resultSet.getString("description"));
                    book.setCategoryId(resultSet.getInt("category_id"));
                    book.setCategory(resultSet.getString("category_name"));
                    book.setFormatId(resultSet.getInt("format_id"));
                    book.setFormat(resultSet.getString("format_name"));
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
        List<Book> sortedList = books.stream()
                .sorted(Comparator.comparingInt(Book::getId))
                .collect(Collectors.toList());
        return sortedList;
    }

    public int deleteBookAuthors (int bookId) {
        int result = 0;
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_AUTHORS_TO_BOOKS);
            statement.setInt(1, bookId);
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

    public int setBookAuthors (int bookId, List <Integer> authorIds) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_AUTHORS_TO_BOOKS);
            for (int author: authorIds) {
                statement.setInt(1, bookId);
                statement.setInt(2, author);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }

    @Override
    public int deleteByIdLang(int id, String lang) {
        return 0;
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

    public List<Book> filterByCategory (List<Book> books, int [] categoryIds) {
      List <Book> result = new ArrayList<>();
        for (Book book: books) {
            for (int categoryId : categoryIds) {
                if (book.getCategoryId() == categoryId) {
                    result.add(book);
                    continue;
                }
            }
        }
        return result;
    }

    public List<Book> filterByFormat (List<Book> books, int[] formatIds) {
            List <Book> result = new ArrayList<>();
            for (Book book: books) {
                for (int formatId : formatIds) {
                    if (book.getFormatId() == formatId) {
                        result.add(book);
                        continue;
                    }
                }
            }
            return result;
    }

    public List<Book> filterByPublLang (List<Book> books, String [] publLangs) {
        List <Book> result = new ArrayList<>();
        for (Book book: books) {
            for (String lang : publLangs) {
                if (book.getLanguage().equals(lang)) {
                    result.add(book);
                    continue;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BookDaoImpl impl = new BookDaoImpl();

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
