package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Teacher;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
public class JdbcTeacherDAO implements TeacherDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/teacherQueries.properties").loadProperty();
    private static final String ADD = "addTeacher";
    private static final String FIND_BY_ID = "findTeacherById";
    private static final String FIND_ALL = "findAllTeacher";
    private static final String REMOVE = "removeTeacherById";
    private static final String FIND_WITH_EQUAL_DEGREE = "findAllTeachersWithEqualDegree";

    @Autowired
    public JdbcTeacherDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Teacher teacher) {
        jdbcTemplate.update(properties.getProperty(ADD), teacher.getFirstName(), teacher.getLastNAme(), teacher.getSex(),
                teacher.getAge(), teacher.getDegree(), teacher.getSchedule().getId());
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty(REMOVE), id);
    }

    @Override
    public Teacher findById(int id) throws NotImplementedException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Teacher.class), id)
                .stream().findAny().orElseThrow(() -> new NotImplementedException("Teacher not found - " + id));
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Teacher.class));
    }

    @Override
    public Integer findTeachersCountWithEqualDegree(String degree) {
        return jdbcTemplate.queryForObject(properties.getProperty(FIND_WITH_EQUAL_DEGREE), Integer.class, degree);
    }
}
