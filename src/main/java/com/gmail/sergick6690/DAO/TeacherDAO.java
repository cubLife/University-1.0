package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Teacher;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;

import java.util.List;

public interface TeacherDAO extends CrudMethods<Teacher> {

    @Override
    default void add(Teacher obj) {
    }

    @Override
    default Teacher findById(int id) throws NotImplementedException {
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

    public void removeSchedule(int teacherId);

    public void assignSchedule(int teacherId, int scheduleId);
}
