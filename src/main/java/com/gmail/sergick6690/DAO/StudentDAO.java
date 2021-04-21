package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Student;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;

import java.util.List;

public interface StudentDAO extends CrudMethods<Student> {
    @Override
    default void add(Student obj) {
    }

    @Override
    default Student findById(int id) throws NotImplementedException {
        return null;
    }

    @Override
    default List<Student> findAll() {
        return null;
    }

    @Override
    default void removeById(int id) {
    }

    public void assignGroup(int studentId, int groupId);

    public void removeFromGroup(int studentId);

    public void assignCourse(int studentId, int course);

    public void removeFromCourse(int studentId);
}
