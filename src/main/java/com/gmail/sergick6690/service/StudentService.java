package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.implementation.JdbcStudentDAO;
import com.gmail.sergick6690.university.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

    public void assignGroup(int studentId, int groupId) {
        studentDAO.assignGroup(groupId, studentId);
    }

    public void removeFromGroup(int studentId) {
        studentDAO.removeFromGroup(studentId);

    }

    public void assignCourse(int studentId, int course) {
        studentDAO.assignCourse(studentId, course);
    }

    public void removeFromCourse(int studentId) {
        studentDAO.removeFromCourse(studentId);
    }

    public void changeGroup(int studentId, int groupId) {
        studentDAO.removeFromGroup(studentId);
        studentDAO.assignGroup(studentId, groupId);
    }

    public void changeCourse(int studentId, int course) {
        studentDAO.removeFromCourse(studentId);
        studentDAO.assignCourse(studentId, course);
    }
}