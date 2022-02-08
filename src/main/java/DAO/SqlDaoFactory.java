package DAO;
import DAO.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SqlDaoFactory {

    private static SqlDaoFactory instance = new SqlDaoFactory();

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    private static final Map<String, BaseDao> DAO_MAP = new HashMap<>();
   /* private UserDao userDao;
    private BookDao bookDao;
    private CategoryDao categoryDao;
    private OrderDao orderDao;
    private LanguageDao languageDao;
    private WishListDao wishListDao;
    private AuthorDao authorDao;
    private FormatDao formatDao;
    private CartDao cartDao;
    private CardDao cardDao;
    private AddressDao addressDao;
    private StatusDao statusDao;
*/
    static {
        DAO_MAP.put("users", new UserDaoImpl());
        DAO_MAP.put("books", new BookDaoImpl());
        DAO_MAP.put("categories", new CategoryDaoImpl());
        DAO_MAP.put("categories_lang", new CategoryDaoImpl());
        DAO_MAP.put("authors", new AuthorDaoImpl());
        DAO_MAP.put("authors_lang", new AuthorDaoImpl());
        DAO_MAP.put("formats", new FormatDaoImpl());
        DAO_MAP.put("carts", new CartDaoImpl());
        DAO_MAP.put("cards", new CardDaoImpl());
        DAO_MAP.put("addresses", new AddressDaoImpl());
        DAO_MAP.put("statuses_lang", new StatusDaoImpl());
        DAO_MAP.put("wish_lists", new WishListDaoImpl());
    }

    public static SqlDaoFactory getInstance() {
        return instance;
    }

    public BaseDao getDao(String table) {
        BaseDao dao = null;
        try {
            dao = DAO_MAP.get(table);
        }
        catch (NullPointerException e) {
            LOGGER.info(e);
        }
        return dao;
    }

/*
 private SqlDaoFactory() {
     userDao = new UserDaoImpl();
     bookDao =  new BookDaoImpl();
     categoryDao = new CategoryDaoImpl();
     orderDao = new OrderDaoImpl();
     languageDao = new LanguageDaoImpl();
     wishListDao = new WishListDaoImpl();
     authorDao = new AuthorDaoImpl();
     formatDao = new FormatDaoImpl();
     cartDao = new CartDaoImpl();
     cardDao = new CardDaoImpl();
     addressDao = new AddressDaoImpl();
     statusDao = new StatusDaoImpl();
 }

    public StatusDao getStatusDao() {
        return statusDao;
    }

    public AddressDao getAddressDao() {
        return addressDao;
    }

    public static SqlDaoFactory getInstance() {
        return instance;
    }

    public CardDao getCardDao() {
        return cardDao;
    }

    public CartDao getCartDao() {
        return cartDao;
    }

    public FormatDao getFormatDao() {
        return formatDao;
    }

    public AuthorDao getAuthorDao() {
        return authorDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }
    public OrderDao getOrderDao() {
        return orderDao;
    }
    public LanguageDao getLanguageDao() {
        return languageDao;
    }
    public WishListDao getWishListDao() {
        return wishListDao;
    }*/

}
