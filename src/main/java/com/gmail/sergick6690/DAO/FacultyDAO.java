package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Faculty;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;

import java.util.List;

public interface FacultyDAO extends CrudMethods<Faculty> {
    @Override
    default void add(Faculty obj) {
    }

    @Override
    default Faculty findById(int id) throws NotImplementedException {
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
