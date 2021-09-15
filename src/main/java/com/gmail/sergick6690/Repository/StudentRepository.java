package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Student;

import java.util.List;

public interface StudentRepository extends GenericRepository<Student> {
    @Override
    default void add(Student obj) throws RepositoryException {
    }

    @Override
    default Student findById(int id) throws RepositoryException {
        return null;
    }

    @Override
    default List<Student> findAll() throws RepositoryException {
        return null;
    }

    @Override
    default void removeById(int id) throws RepositoryException {
    }

    public void assignCourse(int studentId, int course) throws RepositoryException;

    public void removeFromCourse(int studentId) throws RepositoryException;
}