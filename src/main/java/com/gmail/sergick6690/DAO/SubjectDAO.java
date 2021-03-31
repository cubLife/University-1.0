package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Subject;

import java.sql.SQLException;
import java.util.List;

public interface SubjectDAO {
    public void addSubject(Subject subject);

    public Subject findSubjectById(int id) throws SQLException;

    public List<Subject> findAllSubjects();

    public void removeSubjectById(int id);

    public List<Subject> findAllSubjectRelatedToAudience(int id);
}
