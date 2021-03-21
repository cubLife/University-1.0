package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.Teacher;

import java.util.List;

public interface TeacherDAO {

    public void addTeacher(Teacher teacher);

    public void removeTeacherById(int id);

    public Teacher findTeacherById(int id);

    public List<Teacher> findAllTeacher();

    public Integer findAllTeachersWithEqualDegree(String degree);
}
