package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Subject;

import java.sql.SQLException;
import java.util.List;

public interface SubjectDAO extends CrudMethods<Subject> {
    @Override
    default void add(Subject obj) {
    }

    @Override
    default Subject findById(int id) throws SQLException {
        return null;
    }

    @Override
    default List<Subject> findAll() {
        return null;
    }

    @Override
    default void removeById(int id) {
    }

    public List<Subject> findAllSubjectRelatedToAudience(int id);
}
