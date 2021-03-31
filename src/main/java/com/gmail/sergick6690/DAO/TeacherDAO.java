package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Teacher;

import java.sql.SQLException;
import java.util.List;

public interface TeacherDAO {

    public void addTeacher(Teacher teacher);

    public void removeTeacherById(int id);

    public Teacher findTeacherById(int id) throws SQLException;

    public List<Teacher> findAllTeacher();

    public Integer findTeachersCountWithEqualDegree(String degree);
}
