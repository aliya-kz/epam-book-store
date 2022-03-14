package dao.impl;

import dao.*;
import dao.db_connection.ConnectionPool;
import com.lambdaworks.crypto.SCryptUtil;
import entity.Address;
import entity.Card;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import passwordEncr.PasswordEncrypter;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static dao.DaoConstants.*;

public class UserDaoImpl implements UserDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String INSERT_USER = "INSERT into users" +
            "(name, surname, date_of_birth, email, phone, password, is_admin) " +
            "VALUES " + "(?,?,?,?,?,?,?);";

    private final static String GET_USER = "SELECT u.*, c.card_number, c.card_id, a.address, a.address_id FROM users u " +
            "LEFT JOIN cards c ON u.id=c.user_id LEFT JOIN addresses a ON u.id=a.user_id " +
            "WHERE u.id = ?;";

    private final static String VALIDATE_USER = "SELECT id, password from users WHERE email = ?;";

    private final static String INSERT_ADDRESS = "INSERT into addresses (user_id, address) values (?, ?);";

    private final static String INSERT_CARD = "INSERT INTO cards (user_id, card_number) VALUES (?,?);";

    private final static String SELECT_ALL_SQL = "SELECT * FROM users;";

    private final static String SELECT_WHERE_EMAIL = "SELECT id FROM users WHERE email = ?";

    private final static String UPDATE_PASSWORD_SQL = "UPDATE users set password = ? WHERE id = ?";

    private final static String CHECK_ADMIN_SQL = "SELECT is_admin from users where email = ?;";

    private final static String GET_ID = "SELECT id FROM users WHERE email = ?;";

    private final static String SELECT_PASS = "SELECT password FROM users WHERE id = ?;";


    @Override
    public boolean deleteById(long id) {

        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }

    @Override
    public boolean deleteByIdLang(long id, String lang) {
        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }

    @Override
    public boolean addEntity(User user) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        try (PreparedStatement insertUser = connection.prepareStatement(INSERT_USER,
                Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertAddress = connection.prepareStatement(INSERT_ADDRESS);
             PreparedStatement insertCard = connection.prepareStatement(INSERT_CARD);) {
            connection.setAutoCommit(false);
            long userId = 0;
            insertUser.setString(1, user.getName());
            insertUser.setString(2, user.getSurname());
            insertUser.setDate(3, user.getDateOfBirth());
            insertUser.setString(4, user.getEmail());
            insertUser.setString(5, user.getPhone());
            String encryptedPassword = PasswordEncrypter.encrypt(user.getPassword());
            insertUser.setString(6, encryptedPassword);
            insertUser.setBoolean(7, user.getIsAdmin());
            insertUser.executeUpdate();
            ResultSet rs = insertUser.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getLong(ID);
            }

            Address address = user.getAddresses().get(0);
            insertAddress.setLong(1, userId);
            insertAddress.setString(2, address.getAddress());
            insertAddress.executeUpdate();

            Card card = user.getCards().get(0);
            insertCard.setLong(1, userId);
            insertCard.setString(2, card.getCardNumber());
            insertCard.executeUpdate();
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

    public long getIdByEmail(String email) {
        Connection connection = connectionPool.takeConnection();
        long id = 0;
        try (PreparedStatement statement = connection.prepareStatement(GET_ID);) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getLong(ID);
            }
        } catch (SQLException e) {
            LOGGER.info(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return id;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try (Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(ID));
                user.setEmail(resultSet.getString(EMAIL));
                user.setName(resultSet.getString(NAME));
                user.setSurname(resultSet.getString(SURNAME));
                user.setPhone(resultSet.getString(PHONE));
                user.setDateOfBirth(resultSet.getDate(DATE_OF_BIRTH));
                user.setBlocked(resultSet.getBoolean(IS_BLOCKED));
                users.add(user);
            }
        } catch (SQLException ex) {
            LOGGER.info(ex);
            ex.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users.stream()
                .sorted(Comparator.comparingLong(User::getId))
                .collect(Collectors.toList());
    }

    public boolean addAddress(long id, String address) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_ADDRESS);
            statement.setLong(1, id);
            statement.setString(2, address);
            statement.executeUpdate();
        } catch (SQLException e) {
            result = false;
            LOGGER.info(e);
        } finally {
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
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                status = resultSet.getBoolean(IS_ADMIN);
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

    public boolean blockUser(long id, boolean status) {
        BaseDao dao = new UserDaoImpl();
        return dao.setColumnValue(USERS, id, IS_BLOCKED, status);
    }

    public User getUser(long id) {
        User user = new User(id);
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement  = connection.prepareStatement(GET_USER);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (user.getName() == null) {
                    List<Address> addresses = new ArrayList<>();
                    Address address = new Address(resultSet.getInt(ADDRESS_ID), id,
                            resultSet.getString(ADDRESS));
                    addresses.add(address);
                    user.setAddresses(addresses);
                    List<Card> cards = new ArrayList<>();
                    Card card = new Card(resultSet.getInt(CARD_ID), id, resultSet.getString(CARD_NUMBER));
                    cards.add(card);
                    user.setCards(cards);
                    user.setEmail(resultSet.getString(EMAIL));
                    user.setName(resultSet.getString(NAME));
                    user.setSurname(resultSet.getString(SURNAME));
                    user.setPhone(resultSet.getString(PHONE));
                    user.setDateOfBirth(resultSet.getDate(DATE_OF_BIRTH));
                    user.setBlocked(resultSet.getBoolean(IS_BLOCKED));
                    user.setAdmin(resultSet.getBoolean(IS_ADMIN));
                } else {
                    Address address = new Address(resultSet.getInt(ADDRESS_ID), id,
                            resultSet.getString(ADDRESS));
                    Card card = new Card(resultSet.getInt(CARD_ID), id, resultSet.getString(CARD_NUMBER));
                    if (!user.getAddresses().contains(address)) {
                        user.getAddresses().add(address);
                    }
                    if (!user.getCards().contains(card)) {
                        user.getCards().add(card);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    public boolean validateUser(String email, String password) {
        boolean result = false;
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(VALIDATE_USER);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String sqlPassword = resultSet.getString(PASSWORD);
                if (SCryptUtil.check(password, sqlPassword)) {
                    result = true;
                }
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
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

    public boolean changePassword(long id, String oldPass, String newPass) {
        boolean result = true;
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        try {
            statement = connection.prepareStatement(SELECT_PASS);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String sqlPass = resultSet.getString(PASSWORD);
                boolean pasCorrect = SCryptUtil.check(oldPass, sqlPass);
                if (pasCorrect) {
                    String newEncrPas = PasswordEncrypter.encrypt(newPass);
                    statement1 = connection.prepareStatement(UPDATE_PASSWORD_SQL);
                    statement1.setLong(2, id);
                    statement1.setString(1, newEncrPas);
                    statement1.executeUpdate();
                }
            }
        } catch (SQLException e) {
            LOGGER.info(e);
            e.printStackTrace();
            result = false;
        } finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return result;
    }
}
