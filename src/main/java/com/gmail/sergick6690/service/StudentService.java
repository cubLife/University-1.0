package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.implementation.JdbcStudentDAO;
import com.gmail.sergick6690.university.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements CrudMethods<Student> {
    private StudentDAO studentDAO;

    @Autowired
    public StudentService(JdbcStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public void add(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        studentDAO.add(student);
    }

    @Override
    public Student findById(int id) {
        return studentDAO.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDAO.findAll();
    }

    @Override
    public void removeById(int id) {
        studentDAO.removeById(id);
    }
}