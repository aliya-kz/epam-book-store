package dao.impl;
import dao.FormatDao;
import dao.db_connection.ConnectionPool;
import entity.Format;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static dao.DaoConstants.*;


public class FormatDaoImpl implements FormatDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String GET_ALL_FORMATS= "SELECT * FROM formats_lang where lang = ?";


    @Override
    public List<Format> getAll(String lang) {
        List <Format> formats = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_FORMATS)) {
            statement.setString(1, lang);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Format format = new Format();
                format.setId(resultSet.getInt(ID));
                format.setFormatName(resultSet.getString(FORMAT_NAME));
                format.setLang(resultSet.getString(LANG));
                formats.add(format);
            }
        } catch (SQLException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        }
        finally {
            connectionPool.returnConnection(connection);
        }
        return formats.stream()
                .sorted(Comparator.comparing(Format::getFormatName))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(long id) {
        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }

    @Override
    public boolean deleteByIdLang(long id, String lang) {

        throw new UnsupportedOperationException(UNSUPPORTED_MESSAGE);
    }
}
