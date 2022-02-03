package DAO;

import entity.Status;

import java.util.List;

public interface StatusDao extends BaseDao <Status> {
    List<Status> getAll (String lang);
}
