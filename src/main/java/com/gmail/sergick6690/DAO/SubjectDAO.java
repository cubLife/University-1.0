package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.Subject;

import java.util.List;

public interface SubjectDAO {
    public void addSubject(Subject subject);

    public Subject findSubjectById(int id);

    public List<Subject> findAllSubjects();

    public void removeSubjectById(int id);

    public List<Subject> findAllSubjectRelatedToAudience(int id);
}
