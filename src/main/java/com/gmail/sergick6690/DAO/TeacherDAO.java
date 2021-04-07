package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Teacher;

import java.sql.SQLException;
import java.util.List;

public interface TeacherDAO extends CrudMethods<Teacher> {

    @Override
    default void add(Teacher obj) {
    }

    @Override
    default Teacher findById(int id) throws SQLException {
        return null;
    }

    @Override
    default List<Teacher> findAll() {
        return null;
    }

    @Override
    default void removeById(int id) {
    }

    public Integer findTeachersCountWithEqualDegree(String degree);
}
