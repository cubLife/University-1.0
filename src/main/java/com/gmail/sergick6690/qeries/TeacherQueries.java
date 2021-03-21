package com.gmail.sergick6690.qeries;

import com.gmail.sergick6690.PropertyLoader;

import java.util.Properties;

public class TeacherQueries {
    private PropertyLoader loader=new PropertyLoader("Queries/teacherQueries.properties");
   private String addTeacher;
    private String removeTeacherById;
    private String findTeacherById;
    private String findAllTeacher;
    private String findAllTeachersWithEqualDegree;

    public TeacherQueries(){
        Properties properties = loader.loadProperty();
        addTeacher=properties.getProperty("addTeacher");
        removeTeacherById=properties.getProperty("removeTeacherById");
        findTeacherById=properties.getProperty("findTeacherById");
        findAllTeacher=properties.getProperty("findAllTeacher");
        findAllTeachersWithEqualDegree=properties.getProperty("findAllTeachersWithEqualDegree");
    }

    public PropertyLoader getLoader() {
        return loader;
    }

    public String getAddTeacher() {
        return addTeacher;
    }

    public String getRemoveTeacherById() {
        return removeTeacherById;
    }

    public String getFindTeacherById() {
        return findTeacherById;
    }

    public String getFindAllTeacher() {
        return findAllTeacher;
    }

    public String getFindAllTeachersWithEqualDegree() {
        return findAllTeachersWithEqualDegree;
    }
}
