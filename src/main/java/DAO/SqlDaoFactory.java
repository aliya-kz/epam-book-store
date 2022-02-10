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
}
