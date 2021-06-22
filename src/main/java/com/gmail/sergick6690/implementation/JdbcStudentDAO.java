package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
public class JdbcStudentDAO implements StudentDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/studentQueries.properties").loadProperty();
    private static final String ADD = "addStudent";
    private static final String FIND_BY_ID = "findStudentById";
    private static final String FIND_ALL = "findAllStudents";
    private static final String REMOVE = "removeStudentById";
    private static final String ASSIGN_GROUP = "assignGroup";
    private static final String REMOVE_FROM_GROUP = "removeFromGroup";
    private static final String ASSIGN_COURSE = "assignCourse";
    private static final String REMOVE_FROM_COURSE = "removeFromCourse";

    @Autowired
    public JdbcStudentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Student student) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ADD), student.getFirstName(), student.getLastName(),
                    student.getSex(), student.getAge(), student.getGroupId(), student.getCourse());
        } catch (Exception e) {
            throw new DaoException("Can't add student - " + student, e);
        }
    }

    @Override
    public Student findById(int id) throws DaoException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Student.class), id)
                .stream().findAny().orElseThrow(() -> new DaoException("Student not found - " + id));
    }

    @Override
    public List<Student> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Student.class));
        } catch (Exception e) {
            throw new DaoException("Can't find any student", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE), id);
        } catch (Exception e) {
            throw new DaoException("Can't remove student with id - " + id, e);
        }
    }

    @Override
    public void assignGroup(int studentId, int groupId) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ASSIGN_GROUP), groupId, studentId);
        } catch (Exception e) {
            throw new DaoException("Can't assign group", e);
        }
    }

    @Override
    public void removeFromGroup(int studentId) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE_FROM_GROUP), studentId);
        } catch (Exception e) {
            throw new DaoException("Can't remove remove student with id - " + studentId + " from group", e);
        }
    }

    @Override
    public void assignCourse(int studentId, int courseId) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ASSIGN_COURSE), courseId, studentId);
        } catch (Exception e) {
            throw new DaoException("Can't assgn course - " + courseId + "for student - " + studentId, e);
        }
    }

    @Override
    public void removeFromCourse(int studentId) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE_FROM_COURSE), studentId);
        } catch (Exception e) {
            throw new DaoException("Can't remove student with id - " + studentId + " from course", e);
        }
    }
}