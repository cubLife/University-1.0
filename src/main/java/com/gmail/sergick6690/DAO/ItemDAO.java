package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends CRUD<Item> {
    @Override
    default void add(Item obj) {

    }

    @Override
    default Item findById(int id) throws Exception {
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
