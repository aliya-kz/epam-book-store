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

    public int setByteImage (int id, String url) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try {
            File file = new File(url);
            FileInputStream fis = new FileInputStream(file);
            statement = connection.prepareStatement(INSERT_COVER);
            statement.setInt(1, id);
            statement.setBinaryStream(2, fis, file.length());
            result = statement.executeUpdate();
            fis.close();
            statement.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public static void main(String[] args) throws IOException {

        BookDao impl = new BookDaoImpl();

        impl.setByteImage(1, "D:/cover/volki.jpg");
        impl.setByteImage(2, "D:/cover/head_first.jpg");
        impl.setByteImage(3, "D:/cover/vse_dlya_prazdnika.jpg");
        impl.setByteImage(4, "D:/cover/yaponskiy.jpg");
        impl.setByteImage(5, "D:/cover/sherlock.jpg");
        impl.setByteImage(6, "D:/cover/do_vstrechi.jpg");
        impl.setByteImage(7, "D:/cover/pozhayu.jpg");
        impl.setByteImage(8, "D:/cover/vospitanie.jpg");
        impl.setByteImage(9, "D:/cover/pinocchio.jpg");
        impl.setByteImage(10, "D:/cover/dumovochka.jpg");
        impl.setByteImage(11, "D:/cover/ekkel.jpeg");
        impl.setByteImage(12, "D:/cover/1q84.jpg");
        impl.setByteImage(13, "D:/cover/norwegian_wood.jpg");
        impl.setByteImage(14, "D:/cover/hr_phylosopher_stone.jpg");
        impl.setByteImage(15, "D:/cover/hp_dealthy_hallows.jpg");
        impl.setByteImage(16, "D:/cover/hp_azkaban.jpg");
        impl.setByteImage(17, "D:/cover/rich_dad.jpg");
        impl.setByteImage(18, "D:/cover/dovlatov.jpg");
        impl.setByteImage(19, "D:/cover/master.jpg");
        impl.setByteImage(20, "D:/cover/sobache.jpg");
        impl.setByteImage(21, "D:/cover/london_favourite.jpg");
        impl.setByteImage(22, "D:/cover/londonc.jpg");
        impl.setByteImage(23, "D:/cover/jamie.jpg");
        impl.setByteImage(24, "D:/cover/5ingr.jpg");
        impl.setByteImage(25, "D:/cover/earth.jpg");
        impl.setByteImage(26, "D:/cover/will.jpg");
        impl.setByteImage(27, "D:/cover/hp_audio.jpg");
/*
        impl.updateByteImage("authors", 1, "D:/authors/fel.jpg");
        impl.updateByteImage("authors", 2, "D:/authors/profile.jpg" );
        impl.updateByteImage("authors", 3, "D:/authors/profile.jpg" );
        impl.updateByteImage("authors", 4, "D:/authors/profile.jpg" );
        impl.updateByteImage("authors", 5, "D:/authors/profile.jpg" );
        impl.updateByteImage("authors", 6, "D:/authors/doyle.jpg" );
        impl.updateByteImage("authors", 7, "D:/authors/moyes.jpg" );
        impl.updateByteImage("authors", 8, "D:/authors/profile.jpg" );
        impl.updateByteImage("authors", 9,  "D:/authors/jose.jpg" );
        impl.updateByteImage("authors", 10, "D:/authors/profile.jpg" );
        impl.updateByteImage("authors", 11, "D:/authors/andersen.jpg" );
        impl.updateByteImage("authors", 12, "D:/authors/eckel.jpg" );
        impl.updateByteImage("authors", 13, "D:/authors/bulgakov.jpg" );
        impl.updateByteImage("authors", 14, "D:/authors/london.jpg" );
        impl.updateByteImage("authors", 15,  "D:/authors/murakami.jpg" );
        impl.updateByteImage("authors", 16,  "D:/authors/kiyosaki.jpg" );
        impl.updateByteImage("authors", 17,  "D:/authors/dovlatov.jpg" );
        impl.updateByteImage("authors", 18, "D:/authors/rowling.jpg" );
        impl.updateByteImage("authors", 19,  "D:/authors/oliver.jpg" );
        impl.updateByteImage("authors", 20, "D:/authors/smith.jpg");
        impl.updateByteImage("authors", 21, "D:/authors/profile.jpg" );
        impl.updateByteImage("authors", 22,  "D:/authors/wells.jpg" );*/
    }
}


