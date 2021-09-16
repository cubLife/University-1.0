package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Cathedra;

import java.util.List;

public interface CathedraRepository extends GenericRepository<Cathedra> {
    @Override
    default void add(Cathedra obj) throws RepositoryException {
    }

    @Override
    default Cathedra findById(int id) throws RepositoryException {
        return null;
    }

    @Override
    default List<Cathedra> findAll() throws RepositoryException {
        return null;
    }

    @Override
    default void removeById(int id) throws RepositoryException {
    }
}
