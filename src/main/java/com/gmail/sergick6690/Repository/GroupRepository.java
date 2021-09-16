package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Group;

import java.util.List;

public interface GroupRepository extends GenericRepository<Group> {
    @Override
    default void add(Group obj) throws RepositoryException {
    }

    @Override
    default Group findById(int id) throws RepositoryException {
        return null;
    }

    @Override
    default List<Group> findAll() throws RepositoryException {
        return null;
    }

    @Override
    default void removeById(int id) throws RepositoryException {
    }

    public List<Group> findAllGroupsRelatedToCathedra(int id) throws RepositoryException;
}
