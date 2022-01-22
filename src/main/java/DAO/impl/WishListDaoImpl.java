package DAO.impl;

import DAO.WishListDao;
import entity.Entity;
import entity.WishList;

import java.util.List;

public class WishListDaoImpl implements WishListDao {


    @Override
    public int addEntity(WishList wishList) {
        return 0;
    }

    @Override
    public List<WishList> getAll() {
        return null;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }

    @Override
    public int addToWishList(int userId, int bookId) {
        return 0;
    }

    @Override
    public int deleteFromWishList(int userId, int bookId) {
        return 0;
    }
    }

