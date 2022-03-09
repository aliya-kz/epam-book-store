package dao.impl;
import dao.FormatDao;
import dao.db_connection.ConnectionPool;
import entity.Format;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FormatDaoImpl implements FormatDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    private final static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private final static String GET_ALL_FORMATS= "SELECT * FROM formats_lang where lang = ?";

    @Override
    public int addEntity(Format format) {
        return 0;
    }

    @Override
    public List<Format> getAll(String lang) {
        List <Format> formats = new ArrayList<>();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement statement = null;
        try { statement = connection.prepareStatement(GET_ALL_FORMATS);
            statement.setString(1, lang);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Format format = new Format();
                format.setId(resultSet.getInt("id"));
                format.setFormatName(resultSet.getString("format_name"));
                format.setLang(resultSet.getString("lang"));
                formats.add(format);
            }
        } catch (Exception e) {
            LOGGER.warn(e);
            e.printStackTrace();
        }
        finally {
            close(statement);
            connectionPool.returnConnection(connection);
        }
        return formats.stream()
                .sorted(Comparator.comparing(Format::getFormatName))
                .collect(Collectors.toList());
    }

    @Override
    public int deleteById(long id) {
        return 0;
    }

    @Override
    public int deleteByIdLang(long id, String lang) {
        return 0;
    }
}
