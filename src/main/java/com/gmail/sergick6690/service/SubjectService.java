package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.DAO.SubjectDAO;
import com.gmail.sergick6690.implementation.JdbcSubjectDAO;
import com.gmail.sergick6690.university.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService implements CrudMethods<Subject> {
    private SubjectDAO subjectDAO;

    @Autowired
    public SubjectService(JdbcSubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    @Override
    public void add(Subject subject) {
        if (subject == null) {
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        subjectDAO.add(subject);
    }

    @Override
    public Subject findById(int id) {
        return subjectDAO.findById(id);
    }

    @Override
    public List<Subject> findAll() {
        return subjectDAO.findAll();
    }

    @Override
    public void removeById(int id) {
        subjectDAO.removeById(id);
    }

    public List<Subject> findAllSubjectRelatedToAudience(int id) {
        return subjectDAO.findAllSubjectRelatedToAudience(id);
    }

    public void assignTeacher(int subjectId, int teacherId) {
        subjectDAO.assignTeacher(subjectId, teacherId);
    }

    public void removeTeacher(int subjectId) {
        subjectDAO.removeTeacher(subjectId);
    }

    public void changeTeacher(int subjectId, int teacherId) {
        subjectDAO.removeTeacher(subjectId);
        assignTeacher(subjectId, teacherId);
    }
}
