package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Student;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = RepositoryException.class)
public class JpaStudentRepository implements com.gmail.sergick6690.Repository.StudentRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/studentQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllStudents";

    @Override
    public void add(Student student) throws RepositoryException {
        try {
            entityManager.persist(student);
        } catch (Exception e) {
            throw new RepositoryException("Can't add student - " + student, e);
        }
    }

    @Override
    public Student findById(int id) throws RepositoryException {
        try {
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                return student;
            } else {
                throw new IllegalArgumentException("Student not found - " + id);
            }
        } catch (Exception e) {
            throw new RepositoryException("Student not found - " + id, e);
        }
    }

    @Override
    public List<Student> findAll() throws RepositoryException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Student.class).getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Can't find any student", e);
        }
    }

    @Override
    public void removeById(int id) throws RepositoryException {
        try {
            Student student = findById(id);
            entityManager.remove(student);
        } catch (Exception e) {
            throw new RepositoryException("Can't remove student with id - " + id, e);
        }
    }

    @Override
    public void assignCourse(int studentId, int courseId) throws RepositoryException {
        try {
            Student student = findById(studentId);
            student.setCourse(courseId);
        } catch (Exception e) {
            throw new RepositoryException("Can't assign course - " + courseId + "for student - " + studentId, e);
        }
    }

    @Override
    public void removeFromCourse(int studentId) throws RepositoryException {
        try {
            Student student = findById(studentId);
            student.setCourse(0);
        } catch (Exception e) {
            throw new RepositoryException("Can't remove student with id - " + studentId + " from course", e);
        }
    }
}