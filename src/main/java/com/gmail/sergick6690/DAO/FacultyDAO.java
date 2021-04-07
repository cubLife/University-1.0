package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Faculty;

import java.sql.SQLException;
import java.util.List;

public interface FacultyDAO extends CrudMethods<Faculty> {
    @Override
    default void add(Faculty obj) {
    }

    @Override
    default Faculty findById(int id) throws SQLException {
        return null;
    }

    @Override
    default List<Faculty> findAll() {
        return null;
    }

    @Override
    default void removeById(int id) {
    }
}
