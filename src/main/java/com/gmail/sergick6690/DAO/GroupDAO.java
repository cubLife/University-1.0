package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Group;

import java.sql.SQLException;
import java.util.List;

public interface GroupDAO extends CrudMethods<Group> {
    @Override
    default void add(Group obj) {
    }

    @Override
    default Group findById(int id) throws SQLException {
        return null;
    }

    @Override
    default List<Group> findAll() {
        return null;
    }

    @Override
    default void removeById(int id) {
    }

    public List<Group> findAllGroupsRelatedToCathedra(int id);
}
