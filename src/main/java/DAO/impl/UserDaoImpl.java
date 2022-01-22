package DAO.impl;

import DAO.BaseDao;
import DAO.UserDao;
import DAO.db_connection.ConnectionPool;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import passwordEncr.PasswordEncrypter;

import java.sql.*;
import java.util.*;

public class UserDaoImpl implements UserDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static String INSERT_USER = "INSERT into users" +
            "(name, surname, date_of_birth, email, phone, password) " +
            "VALUES " + "(?,?,?,?,?,?);";

    private static String GET_USER= "SELECT u.*, c.card_number, a.address FROM users u " +
            "LEFT JOIN cards c ON u.id=c.user_id LEFT JOIN addresses a ON u.id=a.user_id " +
            "WHERE email = ? AND password = ?;";

    private static String INSERT_ADDRESS = "INSERT into addresses (user_id, address) values (?, ?);";

    private static String INSERT_CARD = "INSERT into cards (user_id, card_number) values (?, ?);";

    private static String SELECT_ALL_SQL = "SELECT u.*, a.address FROM users u left join addresses a on u.id=a.user_id;";

    private static String SELECT_WHERE_EMAIL = "SELECT id FROM users WHERE email = ?";

    private static String UPDATE_PASSWORD_SQL = "UPDATE users set password = ? WHERE email = ? and password = ?";

    private static String CHECK_ADMIN_SQL = "SELECT is_admin from users where email = ?;";

    PasswordEncrypter passwordEncrypter = new PasswordEncrypter();
    @Override
    public int addEntity (User user) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        int id = -1;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        try {statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1,user.getName());
            statement.setString(2,user.getSurname());
            statement.setDate(3, user.getDateOfBirth());
            statement.setString(4,user.getEmail());
            statement.setString(5,user.getPhone());
            String encryptedPassword = passwordEncrypter.encrypt(user.getPassword());
            statement.setString(6,encryptedPassword);
            result = statement.executeUpdate();

            statement1 = connection.prepareStatement("SELECT * FROM users WHERE email = " + user.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }

            List <String> addresses = user.getAddresses();
            addAddress(id, addresses.get(0));
            List <String > cards = user.getCards();
            addCard(id, cards.get(0));
        } catch (Exception e) {
            LOGGER.info(e);
            e.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    @Override
    public List<User> getAll() {
        List <User> users = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                User user = new User(id);
                int index = users.indexOf(user);
                if (index > -1) {
                    User existingUser = users.get(index);
                    existingUser.getAddresses().add(resultSet.getString("address"));
                } else {
                    List<String> addresses = new ArrayList<>();
                    addresses.add(resultSet.getString("address"));
                    user.setAddresses(addresses);
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setDateOfBirth(resultSet.getDate("date_of_birth"));
                    user.setBlocked(resultSet.getBoolean("is_blocked"));
                    users.add(user);
                }
            }
        } catch (SQLException ex) {
            LOGGER.info(ex);
            ex.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }

    public int addAddress (int id, String address) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(INSERT_ADDRESS);
            statement.setInt(1, id);
            statement.setString(2, address);
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(e);
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public int addCard(int id, String card) {
        Connection connection = connectionPool.takeConnection();
        int result = 0;
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(INSERT_CARD);
            statement.setInt(1, id);
            statement.setString(2, card);
            result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(e);
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public boolean isAdmin(String email) {
         boolean status = false;
         Connection connection = connectionPool.takeConnection();
         PreparedStatement statement = null;
         try {
             statement = connection.prepareStatement(CHECK_ADMIN_SQL);
             statement.setString(1,email);
             ResultSet resultSet = statement.executeQuery();
             while (resultSet.next()) {
                 status = resultSet.getBoolean("is_admin");
             }
         } catch (SQLException e) {
             e.printStackTrace();
             LOGGER.info(e);
         } finally {
             close(statement);
             connectionPool.returnConnection(connection);
         }
         return status;
    }

    public int blockUser(int id, boolean status) {
        BaseDao dao = new UserDaoImpl();
        int result = dao.setColumnValue("users", id, "is_blocked", status);
        return result;
    }

    public User validateUser (String email, String password) {
        User user = null;
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(GET_USER);
            statement.setString(1, email);
            statement.setString(2, passwordEncrypter.encrypt(password));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (user == null) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    List<String> addresses = new ArrayList<>();
                    addresses.add(resultSet.getString("address"));
                    List<String> cards = new ArrayList<>();
                    user.setAddresses(addresses);
                    cards.add(resultSet.getString("card_number"));
                    user.setCards(cards);
                    user.setEmail(resultSet.getString("email"));
                    user.setName(resultSet.getString("name"));
                    user.setSurname(resultSet.getString("surname"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setDateOfBirth(resultSet.getDate("date_of_birth"));
                    user.setBlocked(resultSet.getBoolean("is_blocked"));
                    user.setAdmin(resultSet.getBoolean("is_admin"));
                } else
                if (resultSet.getString("address") != null) {
                    user.getAddresses().add(resultSet.getString("address"));
                }
                if (resultSet.getString("address") != null) {
                    user.getAddresses().add(resultSet.getString("address"));
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    public boolean userExists(String email) {
        boolean status = false;
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
           statement = connection.prepareStatement(SELECT_WHERE_EMAIL);
           statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            status = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return status;
    }

    public int changePassword(User user, String newPass) {
        int result = 0;
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        String email = user.getEmail();
        String encryptedPassword = passwordEncrypter.encrypt(user.getPassword());
        String newEncryptedPass = passwordEncrypter.encrypt(newPass);
        try { statement = connection.prepareStatement(UPDATE_PASSWORD_SQL);
            statement.setString(1, newEncryptedPass);
            statement.setString(2, email);
            statement.setString(3, encryptedPassword);
            result= statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public static void main (String [] args) {
        UserDaoImpl impl = new UserDaoImpl();
        System.out.println(impl.isAdmin("qqqqqqa2aaaaaaa@ss"));
        Locale locale = new Locale ("ru");
        ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
        System.out.println("log in  + " + bundle.getString(  "LOG_IN"));
    }

}
