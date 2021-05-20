package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Group;

import java.util.List;

public interface GroupDAO extends GenericDao<Group> {
    @Override
    default void add(Group obj) throws DaoException {
    }

    @Override
    default Group findById(int id) throws DaoException {
        return null;
    }

    @Override
    default List<Group> findAll() throws DaoException {
        return null;
    }

    @Override
    default void removeById(int id) throws DaoException {
    }

    public List<Group> findAllGroupsRelatedToCathedra(int id) throws DaoException;
}
