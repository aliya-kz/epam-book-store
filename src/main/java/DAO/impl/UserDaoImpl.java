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
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

import static dao.DaoConstants.*;

public class UserDaoImpl implements UserDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String INSERT_USER = "INSERT into users" +
            "(name, surname, date_of_birth, email, phone, password, is_admin) " +
            "VALUES " + "(?,?,?,?,?,?,?);";

    private final static String INSERT_FOR_CHECK = "INSERT into users" +
            "(name, surname, date_of_birth, email, phone, password, is_admin, id) " +
            "VALUES " + "(?, ?,?,?,?,?,?,?);";

    private final static String GET_USER= "SELECT u.*, c.card_number, c.card_id, a.address, a.address_id FROM users u " +
            "LEFT JOIN cards c ON u.id=c.user_id LEFT JOIN addresses a ON u.id=a.user_id " +
            "WHERE u.id = ?;";

    private final static String VALIDATE_USER= "SELECT id, password from users WHERE email = ?;";

    private final static String INSERT_ADDRESS = "INSERT into addresses (user_id, address) values (?, ?);";

    private final static String SELECT_ALL_SQL = "SELECT * FROM users;";

    private final static String SELECT_WHERE_EMAIL = "SELECT id FROM users WHERE email = ?";

    private final static String UPDATE_PASSWORD_SQL = "UPDATE users set password = ? WHERE id = ?";

    private final static String CHECK_ADMIN_SQL = "SELECT is_admin from users where email = ?;";

    private final static String GET_ID = "SELECT id FROM users WHERE email = ?;";

    private final static String SELECT_PASS = "SELECT password FROM users WHERE id = ?;";

    @Override
    public boolean deleteById(long id){

        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean deleteByIdLang(long id, String lang){

        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public boolean addEntity (User user) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        try {statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1,user.getName());
            statement.setString(2,user.getSurname());
            statement.setDate(3, user.getDateOfBirth());
            statement.setString(4,user.getEmail());
            statement.setString(5,user.getPhone());
            String encryptedPassword = PasswordEncrypter.encrypt(user.getPassword());
            statement.setString(6,encryptedPassword);
            statement.setBoolean(7, user.getIsAdmin());
            statement.executeUpdate();

            long id = getIdByEmail(user.getEmail());

            List <Address> addresses = user.getAddresses();
            AddressDao addressDao = new AddressDaoImpl();
            Address address = addresses.get(0);
            address.setUserId(id);
            addressDao.addEntity(address);
            List <Card > cards = user.getCards();
            CardDao cardDao = new CardDaoImpl();
            Card card = cards.get(0);
            card.setUserId(id);
            cardDao.addEntity(card);
        } catch (SQLException e) {
            LOGGER.info(e);
            result = false;
        }
        finally {
            close(statement);
            close(statement1);
            connectionPool.returnConnection(connection);
        }
        return result;
    }

    public long getIdByEmail (String email) {
        Connection connection = connectionPool.takeConnection();
        long id = 0;
        PreparedStatement statement = null;
        try {statement = connection.prepareStatement(GET_ID);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getLong(ID);
            }
        } catch (SQLException e) {
            LOGGER.info(e);
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return id;
    }

    public int addForDbCheck (User user) {
        Connection connection = connectionPool.takeConnection();
        int id = -1;
        PreparedStatement statement = null;
        PreparedStatement statement1 = null;
        try {statement = connection.prepareStatement(INSERT_FOR_CHECK);
            statement.setString(1,user.getName());
            statement.setString(2,user.getSurname());
            statement.setDate(3, user.getDateOfBirth());
            statement.setString(4,user.getEmail());
            statement.setString(5,user.getPhone());
            String encryptedPassword = PasswordEncrypter.encrypt(user.getPassword());
            statement.setString(6,encryptedPassword);
            statement.setBoolean(7, user.getIsAdmin());
            statement.setLong(8, user.getId());
            statement.executeUpdate();

            statement1 = connection.prepareStatement(GET_ID);
            statement1.setString(1, user.getEmail());
            ResultSet resultSet = statement1.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(ID);
            }
            List <Address> addresses = user.getAddresses();
            AddressDao addressDao = new AddressDaoImpl();
            Address address = addresses.get(0);
            address.setUserId(id);
            addressDao.addEntity(address);
            List <Card > cards = user.getCards();
            CardDao cardDao = new CardDaoImpl();
            Card card = cards.get(0);
            card.setUserId(id);
            cardDao.addEntity(card);
        } catch (SQLException e) {
            LOGGER.info(e);
            e.printStackTrace();
        }
        finally {
            close(statement);
            close(statement1);
            connectionPool.returnConnection(connection);
        }
        return id;
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
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return users.stream()
                .sorted(Comparator.comparingLong(User::getId))
                .collect(Collectors.toList());
    }

    public boolean addAddress (long id, String address) {
        Connection connection = connectionPool.takeConnection();
        boolean result = true;
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(INSERT_ADDRESS);
            statement.setLong(1, id);
            statement.setString(2, address);
            statement.executeUpdate();
        } catch (SQLException e) {
            result = false;
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

    public User getUser (long id) {
        User user = new User(id);
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(GET_USER);
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
                    Card card = new Card (resultSet.getInt(CARD_ID), id, resultSet.getString(CARD_NUMBER));
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
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    public boolean validateUser (String email, String password) {
        boolean result = false;
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(VALIDATE_USER);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
            String sqlPass = resultSet.getString(PASSWORD);
            if (SCryptUtil.check(password, sqlPass)) {
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

    public static void main (String [] args) {
        UserDaoImpl dao= new UserDaoImpl();
        User admin = new User("admin", "admin", "admin@admin.com", "87771111111", new Date(2000/04/05), "12345", true);
        admin.setId(1);
        List <Address> addresses = new ArrayList<>();
        Address address = new Address(1, 1, "Gogol 2");
        addresses.add(address);
        admin.setAddresses(addresses);
        List <Card> cards = new ArrayList<>();
        Card card = new Card(1,2, "123412341234");
        cards.add(card);
        admin.setCards(cards);
        dao.addForDbCheck(admin);

        User user = new User ("user", "ivanov", "user@user.com", "87775556644", new Date(2000/04/05), "12345", false);
        user.setId(2);
        user.setCards(cards);
        user.setAddresses(addresses);
        dao.addForDbCheck(user);
    }
}
