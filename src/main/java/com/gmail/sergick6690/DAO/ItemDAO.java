package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Item;

import java.util.List;

public interface ItemDAO extends GenericDao<Item> {
    @Override
    default void add(Item obj) throws DaoException {
    }

    @Override
    default Item findById(int id) throws DaoException {
        return null;
    }

    @Override
    default List<Item> findAll() throws DaoException {
        return null;
    }

    @Override
    default void removeById(int id) throws DaoException {
    }
}
