package com.gmail.sergick6690.qeries;

import com.gmail.sergick6690.PropertyLoader;

import java.util.Properties;

public class StudentQueries {
    private PropertyLoader loader = new PropertyLoader("Queries/studentQueries.properties");
    private String addStudent;
    private String findStudentById;
    private String findAllStudents;
    private String removeStudentById;

    public StudentQueries() {
        Properties properties = loader.loadProperty();
        addStudent = properties.getProperty("addStudent");
        findStudentById = properties.getProperty("findStudentById");
        findAllStudents = properties.getProperty("findAllStudents");
        removeStudentById = properties.getProperty("removeStudentById");
    }

    public String getAddStudent() {
        return addStudent;
    }

    public String getFindStudentById() {
        return findStudentById;
    }

    public String getFindAllStudents() {
        return findAllStudents;
    }

    public String getRemoveStudentById() {
        return removeStudentById;
    }
}
