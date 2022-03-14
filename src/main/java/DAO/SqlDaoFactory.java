package dao;
import dao.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

import static dao.DaoConstants.*;

public class SqlDaoFactory {

    private static SqlDaoFactory INSTANCE;

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());

    private static final Map<String, BaseDao> DAO_MAP = new HashMap<>();


    static {
        DAO_MAP.put(USERS, new UserDaoImpl());
        DAO_MAP.put(BOOKS, new BookDaoImpl());
        DAO_MAP.put(CATEGORIES, new CategoryDaoImpl());
        DAO_MAP.put(CATEGORIES_LANG, new CategoryDaoImpl());
        DAO_MAP.put(AUTHORS, new AuthorDaoImpl());
        DAO_MAP.put(AUTHORS_LANG, new AuthorDaoImpl());
        DAO_MAP.put(FORMATS, new FormatDaoImpl());
        DAO_MAP.put(CARTS, new CartDaoImpl());
        DAO_MAP.put(CARDS, new CardDaoImpl());
        DAO_MAP.put(ADDRESSES, new AddressDaoImpl());
        DAO_MAP.put(STATUSES_LANG, new StatusDaoImpl());
        DAO_MAP.put(WISH_LISTS, new WishListDaoImpl());
    }

    private SqlDaoFactory () {}

    public static SqlDaoFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (SqlDaoFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SqlDaoFactory();
                }
            }
        }
        return INSTANCE;
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
}
