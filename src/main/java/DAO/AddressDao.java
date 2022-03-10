package dao;

import entity.Address;


public interface AddressDao extends BaseDao <Address> {

    @Override
    boolean addEntity(Address address);
    boolean deleteById(long id);
}
