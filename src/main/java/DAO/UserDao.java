package dao;

import entity.User;
import java.util.List;

public interface UserDao extends BaseDao <User> {

    boolean addEntity (User user);
    boolean addAddress (long id, String address);
    List <User> getAll();
    boolean isAdmin (String email);
    boolean validateUser (String email, String password);
    User getUser (long id);
    boolean userExists (String email);
    boolean blockUser(long id, boolean status);
    boolean changePassword (long id, String oldPass, String newPass);
    long getIdByEmail (String email);

}
