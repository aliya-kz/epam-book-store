package DAO;
import DAO.impl.*;
import entity.Entity;

public class SqlDaoFactory {
    private static SqlDaoFactory instance = new SqlDaoFactory();
    private UserDao userDao;
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
    }

}
