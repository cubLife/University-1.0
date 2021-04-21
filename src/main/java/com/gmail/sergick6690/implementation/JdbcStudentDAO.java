package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Student;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
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
    public void add(Student student) {
        jdbcTemplate.update(properties.getProperty(ADD), student.getFirstName(), student.getLastNAme(),
                student.getSex(), student.getAge(), student.getGroupID(), student.getCourse());
    }

    @Override
    public Student findById(int id) throws NotImplementedException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Student.class), id)
                .stream().findAny().orElseThrow(() -> new NotImplementedException("Student not found - " + id));
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty(REMOVE), id);
    }

    @Override
    public void assignGroup(int studentId, int groupId) {
        jdbcTemplate.update(properties.getProperty(ASSIGN_GROUP), groupId, studentId);
    }

    @Override
    public void removeFromGroup(int studentId) {
        jdbcTemplate.update(properties.getProperty(REMOVE_FROM_GROUP), studentId);

    }

    @Override
    public void assignCourse(int studentId, int course) {
        jdbcTemplate.update(properties.getProperty(ASSIGN_COURSE), course, studentId);
    }

    @Override
    public void removeFromCourse(int studentId) {
        jdbcTemplate.update(properties.getProperty(REMOVE_FROM_COURSE), studentId);
    }
}
