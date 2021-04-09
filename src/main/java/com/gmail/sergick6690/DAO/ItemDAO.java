package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Item;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;

import java.util.List;

public interface ItemDAO extends CrudMethods<Item> {
    @Override
    default void add(Item obj) {
    }

    @Override
    default Item findById(int id) throws NotImplementedException {
        return null;
    }

    @Override
    default List<Item> findAll() {
        return null;
    }

    @Override
    default void removeById(int id) {
    }
}
