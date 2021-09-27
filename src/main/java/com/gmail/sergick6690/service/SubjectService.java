package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.SubjectRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.university.Subject;
import com.gmail.sergick6690.university.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
@Transactional(rollbackFor = ServiceException.class)
public class SubjectService {
    private SubjectRepository subjectRepository;
    private TeacherService teacherService;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");
    private static final int DEFAULT_TEACHER_ID = 1;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, TeacherService teacherService) {
        this.subjectRepository = subjectRepository;
        this.teacherService = teacherService;
    }

    public void add(Subject subject) throws ServiceException {
        if (subject == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            subjectRepository.save(subject);
            DEBUG.debug((format("New subject - %s was added", subject.toString())));
        } catch (NumberFormatException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't add subject - " + subject + e, e);
        }
    }

    public Subject findById(int id) throws ServiceException {
        try {
            Subject subject = subjectRepository.findById(id).get();
            DEBUG.debug(format("Subject with id - %d was returned", id));
            return subject;
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Subject not found - " + id + e, e);
        }
    }

    public List<Subject> findAll() throws ServiceException {
        try {
            List<Subject> subjectList = subjectRepository.findAll();
            DEBUG.debug("All subjects was returned");
            return subjectList;
        } catch (NullPointerException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any subject " + e, e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            subjectRepository.delete(this.findById(id));
            DEBUG.debug(format("Subject with id - %d is removed", id));
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove subject with id - " + id + e, e);
        }
    }

    public List<Subject> findAllSubjectRelatedToAudience(int id) throws ServiceException {
        try {
            List<Subject> subjectList = subjectRepository.findAllSubjectRelatedToAudience(id);
            DEBUG.debug("All subjects related to audience with id - " + id + " was returned");
            return subjectList;
        } catch (NullPointerException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any subjaect related to audience where audience id - " + id + e, e);
        }
    }

    public void assignTeacher(int subjectId, int teacherId) throws ServiceException {
        try {
            Teacher teacher = teacherService.findById(teacherId);
            Subject subject = this.findById(subjectId);
            subject.setTeacher(teacher);
            DEBUG.debug("For subject with id" + subjectId + " was assigned teacher with id - " + teacherId);
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't assign teacher with id - " + teacherId + "for subject with id - " + subjectId + e, e);
        }
    }

    public void removeTeacher(int subjectId) throws ServiceException {
        try {
            Teacher teacher = teacherService.findById(DEFAULT_TEACHER_ID);
            Subject subject = this.findById(subjectId);
            subject.setTeacher(teacher);
            DEBUG.debug("For subject with id - " + subjectId + " was removed teacher");
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove teacher for subject with id - " + subjectId + e, e);
        }
    }

    public void changeTeacher(int subjectId, int teacherId) throws ServiceException {
        try {
            this.removeTeacher(subjectId);
            this.assignTeacher(subjectId, teacherId);
            DEBUG.debug("For subject with id -" + subjectId + " was changed teacher on teacher with id - " + teacherId);
        } catch (NoSuchElementException e) {
            ERROR.error("Can't change teacher for subject with id - " + subjectId, e);
            throw new ServiceException("Can't change teacher for subject with id - " + subjectId + e, e);
        }
    }
}
