package DAO;

import entity.User;
import exception.DaoException;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends BaseDao <User> {
  int addEntity (User user);
  int addAddress (int id, String address);

   boolean isAdmin (String email);
  User validateUser (String email, String password);
  boolean userExists (String email);
   int blockUser(int id, boolean status);
  int changePassword (User user, String newEmail);

}
