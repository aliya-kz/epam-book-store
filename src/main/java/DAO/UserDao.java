package DAO;

import entity.User;
import java.util.List;

public interface UserDao extends BaseDao <User> {
  int addEntity (User user);
  int addAddress (int id, String address);
  List <User> getAll();
   boolean isAdmin (String email);
  int validateUser (String email, String password);
  User getUser (int id);
  boolean userExists (String email);
   int blockUser(int id, boolean status);
  int changePassword (int id, String oldPass, String newPass);

}
