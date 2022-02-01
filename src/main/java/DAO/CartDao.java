package DAO;
import DAO.db_connection.ConnectionPool;
import entity.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;


public interface CartDao extends BaseDao <Cart> {
        int addEntity (Cart cart);
        Cart getCart (int userId);

        @Override
        default int deleteById(String table, int id) {
                ConnectionPool connectionPool = ConnectionPool.getInstance();
                Connection connection = connectionPool.takeConnection();
                int result = 0;
                try {
                        String request = "DELETE from cards WHERE card_id = ?;";
                        PreparedStatement statement = connection.prepareStatement(request);
                        statement.setInt(1, id);
                        result = statement.executeUpdate();
                        close(statement);
                } catch (Exception e) {
                        e.printStackTrace();
                } finally {
                        connectionPool.returnConnection(connection);
                }
                return result;
        }

        int createCart (String email);
        int addToCart (int userId, int bookId, int quantity);
        int deleteFromCart (int cartId, String isbn);
        int getCartCost (int cartId);
}
