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

    private final static String GET_USER = "SELECT * FROM users where id=?";

    private final static String GET_CARDS = "SELECT u.id, c.* FROM users u " +
            " LEFT JOIN cards c ON u.id=c.user_id WHERE u.id = ? and c.is_active=true";

    private final static String GET_ADDRESSES = "SELECT u.id, a.* FROM users u " +
            " LEFT JOIN addresses a ON u.id=a.user_id WHERE u.id = ? and a.is_active=true";

    private final static String VALIDATE_USER = "SELECT id, password from users WHERE email = ?;";

    private final static String INSERT_ADDRESS = "INSERT into addresses (user_id, address) values (?, ?);";

    private final static String INSERT_CARD = "INSERT INTO cards (user_id, card_number) VALUES (?,?);";

    private final static String SELECT_ALL_SQL = "SELECT * FROM users where is_admin = false";

    private final static String SELECT_WHERE_EMAIL = "SELECT id FROM users WHERE email = ?";

    private final static String UPDATE_PASSWORD_SQL = "UPDATE users set password = ? WHERE id = ?";

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
             PreparedStatement insertCard = connection.prepareStatement(INSERT_CARD)) {
            connection.setAutoCommit(false);
            long userId = 0;

            insertUser.setString(1, user.getName());
            insertUser.setString(2, user.getSurname());
            insertUser.setDate(3, user.getDateOfBirth());
            insertUser.setString(4, user.getEmail());
            insertUser.setString(5, user.getPhone());
            String encryptedPassword = PasswordEncrypter.encrypt(user.getPassword());
            insertUser.setString(6, encryptedPassword);
            insertUser.setBoolean(7, user.isAdmin());
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
                    LOGGER.debug(e);
                    connection.rollback();
                } catch (SQLException excep) {
                    LOGGER.debug(excep);
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
        try (PreparedStatement statement = connection.prepareStatement(GET_ID)) {
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
        try (Statement statement = connection.createStatement()) {
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

    public boolean blockUser(long id, boolean status) {
        BaseDao dao = new UserDaoImpl();
        return dao.setColumnValue(USERS, id, IS_BLOCKED, status);
    }

    public List<Card> getCards(long id) {
        List<Card> cards = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement getCards = connection.prepareStatement(GET_CARDS)) {
            getCards.setLong(1, id);
            ResultSet resultSet = getCards.executeQuery();
            while (resultSet.next()) {
                Card card = new Card(resultSet.getInt(CARD_ID), id, resultSet.getString(CARD_NUMBER));
                cards.add(card);
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return cards;
    }

    public List<Address> getAddresses(long id) {
        List<Address> addresses = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement getCards = connection.prepareStatement(GET_ADDRESSES)) {
            getCards.setLong(1, id);
            ResultSet resultSet = getCards.executeQuery();
            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getLong(ADDRESS_ID));
                address.setAddress(resultSet.getString(ADDRESS));
                address.setUserId(id);
                addresses.add(address);
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return addresses;
    }


    public User getUser(long id) {
        User user = new User(id);
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement getUser = connection.prepareStatement(GET_USER)) {
            getUser.setLong(1, id);
            ResultSet resultSet = getUser.executeQuery();
            if (resultSet.next()) {
                user.setEmail(resultSet.getString(EMAIL));
                user.setName(resultSet.getString(NAME));
                user.setSurname(resultSet.getString(SURNAME));
                user.setPhone(resultSet.getString(PHONE));
                user.setDateOfBirth(resultSet.getDate(DATE_OF_BIRTH));
                user.setBlocked(resultSet.getBoolean(IS_BLOCKED));
                user.setAdmin(resultSet.getBoolean(IS_ADMIN));
                List<Card> cards = getCards(id);
                user.setCards(cards);
                List<Address> adresses = getAddresses(id);
                user.setAddresses(adresses);
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
        try (PreparedStatement statement = connection.prepareStatement(VALIDATE_USER)) {
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
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public boolean userExists(String email) {
        boolean status = false;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_WHERE_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            status = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return status;
    }

    public boolean changePassword(long id, String oldPass, String newPass) {
        boolean result = true;
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PASS);
             PreparedStatement statement1 = connection.prepareStatement(UPDATE_PASSWORD_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String sqlPass = resultSet.getString(PASSWORD);
                boolean pasCorrect = SCryptUtil.check(oldPass, sqlPass);
                if (pasCorrect) {
                    String newEncrPas = PasswordEncrypter.encrypt(newPass);
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
            connectionPool.returnConnection(connection);
        }
        return result;
    }
}
