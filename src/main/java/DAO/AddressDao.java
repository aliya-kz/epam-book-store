package DAO;

import entity.Address;


public interface AddressDao extends BaseDao <Address> {
    @Override
    int addEntity(Address address);
}
