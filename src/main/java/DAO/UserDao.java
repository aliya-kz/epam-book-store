package dao;

import entity.User;
import java.util.List;

public interface UserDao extends BaseDao <User> {

    int addEntity (User user);
    int addAddress (long id, String address);
    List <User> getAll();
    boolean isAdmin (String email);
    int validateUser (String email, String password);
    User getUser (long id);
    boolean userExists (String email);
    int blockUser(long id, boolean status);
    int changePassword (long id, String oldPass, String newPass);

}
