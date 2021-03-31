package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    public void addStudent(Student student);

    public Student findStudentById(int id) throws SQLException;

    public List<Student> findAllStudents();

    public void removeStudentById(int id);
}
