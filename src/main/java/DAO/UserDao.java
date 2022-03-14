package dao;

import entity.User;
import java.util.List;

public interface UserDao extends BaseDao {

    boolean addEntity (User user);
    List <User> getAll();
    boolean validateUser (String email, String password);
    User getUser (long id);
    boolean userExists (String email);
    boolean blockUser(long id, boolean status);
    boolean changePassword (long id, String oldPass, String newPass);
    long getIdByEmail (String email);

}
