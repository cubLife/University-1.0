package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.Student;

import java.util.List;

public interface StudentDAO {
    public void addStudent(Student student);

    public Student findStudentById(int id);

    public List<Student> findAllStudents();

    public void removeStudentById(int id);
}
