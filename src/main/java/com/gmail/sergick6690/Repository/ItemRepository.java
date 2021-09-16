package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Item;

import java.util.List;

public interface ItemRepository extends GenericRepository<Item> {
    @Override
    default void add(Item obj) throws RepositoryException {
    }

    @Override
    default Item findById(int id) throws RepositoryException {
        return null;
    }

    @Override
    default List<Item> findAll() throws RepositoryException {
        return null;
    }

    @Override
    default void removeById(int id) throws RepositoryException {
    }
}
