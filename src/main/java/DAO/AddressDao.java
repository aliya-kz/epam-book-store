package dao;

import entity.Address;


public interface AddressDao extends BaseDao {

    boolean addEntity(Address address);
    boolean deleteById(long id);
}
