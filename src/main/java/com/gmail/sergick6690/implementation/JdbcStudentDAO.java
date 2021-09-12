package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Student;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = DaoException.class)
public class JdbcStudentDAO implements StudentDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/studentQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllStudents";

    @Override
    public void add(Student student) throws DaoException {
        try {
            entityManager.persist(student);
        } catch (Exception e) {
            throw new DaoException("Can't add student - " + student, e);
        }
    }

    @Override
    public Student findById(int id) throws DaoException {
        try {
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                return student;
            } else {
                throw new DaoException("Student not found - " + id);
            }
        } catch (Exception e) {
            throw new DaoException("Student not found - " + id, e);
        }
    }

    @Override
    public List<Student> findAll() throws DaoException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Student.class).getResultList();
        } catch (Exception e) {
            throw new DaoException("Can't find any student", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            Student student = findById(id);
            entityManager.remove(student);
        } catch (Exception e) {
            throw new DaoException("Can't remove student with id - " + id, e);
        }
    }

    @Override
    public void assignCourse(int studentId, int courseId) throws DaoException {
        try {
            Student student = findById(studentId);
            student.setCourse(courseId);
        } catch (Exception e) {
            throw new DaoException("Can't assign course - " + courseId + "for student - " + studentId, e);
        }
    }

    @Override
    public void removeFromCourse(int studentId) throws DaoException {
        try {
            Student student = findById(studentId);
            student.setCourse(0);
        } catch (Exception e) {
            throw new DaoException("Can't remove student with id - " + studentId + " from course", e);
        }
    }
}