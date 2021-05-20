package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Cathedra;

import java.util.List;

public interface CathedraDAO extends GenericDao<Cathedra> {
    @Override
    default void add(Cathedra obj) throws DaoException {
    }

    @Override
    default Cathedra findById(int id) throws DaoException {
        return null;
    }

    @Override
    default List<Cathedra> findAll() throws DaoException {
        return null;
    }

    @Override
    default void removeById(int id) throws DaoException {
    }
}
