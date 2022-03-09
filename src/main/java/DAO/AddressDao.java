package dao;

import entity.Address;


public interface AddressDao extends BaseDao <Address> {

    @Override
    int addEntity(Address address);
    int deleteById(long id);
}
