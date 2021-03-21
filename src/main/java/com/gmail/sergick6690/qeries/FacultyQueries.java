package com.gmail.sergick6690.qeries;

import com.gmail.sergick6690.PropertyLoader;

import java.util.Properties;

public class FacultyQueries {
    private PropertyLoader loader = new PropertyLoader("Queries/facultyQueries.properties");
    private String addFaculty;
    private String findFacultyById;
    private String findAllFaculties;
    private String removeFacultyById;

    public FacultyQueries() {
        Properties properties = loader.loadProperty();
        addFaculty = properties.getProperty("addFaculty");
        findFacultyById = properties.getProperty("findFacultyById");
        findAllFaculties = properties.getProperty("findAllFaculties");
        removeFacultyById = properties.getProperty("removeFacultyById");
    }

    public String getAddFaculty() {
        return addFaculty;
    }

    public String getFindFacultyById() {
        return findFacultyById;
    }

    public String getFindAllFaculties() {
        return findAllFaculties;
    }

    public String getRemoveFacultyById() {
        return removeFacultyById;
    }
}
