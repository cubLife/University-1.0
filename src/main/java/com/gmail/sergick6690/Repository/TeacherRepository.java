package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Teacher;

import java.util.List;

public interface TeacherRepository extends GenericRepository<Teacher> {

    @Override
    default void add(Teacher obj) throws RepositoryException {
    }

    @Override
    default Teacher findById(int id) throws RepositoryException {
        return null;
    }

    @Override
    default List<Teacher> findAll() throws RepositoryException {
        return null;
    }

    @Override
    default void removeById(int id) throws RepositoryException {
    }

    public Long findTeachersCountWithEqualDegree(String degree) throws RepositoryException;

}
