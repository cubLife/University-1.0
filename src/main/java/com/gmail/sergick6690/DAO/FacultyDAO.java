package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Faculty;

import java.util.List;

public interface FacultyDAO extends CrudMethods<Faculty> {
    @Override
    default void add(Faculty obj) throws DaoException {
    }

    @Override
    default Faculty findById(int id) throws DaoException {
        return null;
    }

    @Override
    default List<Faculty> findAll() throws DaoException {
        return null;
    }

    @Override
    default void removeById(int id) throws DaoException {
    }
}
