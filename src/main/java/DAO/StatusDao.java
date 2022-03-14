package dao;

import entity.Status;

import java.util.List;

public interface StatusDao extends BaseDao {

    List<Status> getAll (String lang);
}
