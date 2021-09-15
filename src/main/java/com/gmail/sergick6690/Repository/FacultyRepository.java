package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Faculty;

import java.util.List;

public interface FacultyRepository extends GenericRepository<Faculty> {
    @Override
    default void add(Faculty obj) throws RepositoryException {
    }

    @Override
    default Faculty findById(int id) throws RepositoryException {
        return null;
    }

    @Override
    default List<Faculty> findAll() throws RepositoryException {
        return null;
    }

    @Override
    default void removeById(int id) throws RepositoryException {
    }
}
