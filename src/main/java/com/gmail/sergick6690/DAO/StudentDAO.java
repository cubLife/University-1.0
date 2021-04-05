package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO extends CRUD<Student> {
    @Override
    default void add(Student obj) {

    }

    @Override
    default Student findById(int id) throws Exception {
        return null;
    }

    @Override
    default List<Student> findAll() {
        return null;
    }

    @Override
    default void removeById(int id) {

    }
}
