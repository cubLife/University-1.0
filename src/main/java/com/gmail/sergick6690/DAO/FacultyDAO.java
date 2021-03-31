package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Faculty;

import java.sql.SQLException;
import java.util.List;

public interface FacultyDAO {
    public void addFaculty(Faculty faculty);

    public Faculty findFacultyById(int id) throws SQLException;

    public List<Faculty> findAllFaculties();

    public void removeFacultyById(int id);
}
