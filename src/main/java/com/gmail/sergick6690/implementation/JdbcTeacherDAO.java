package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class JdbcTeacherDAO implements TeacherDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/teacherQueries.properties").loadProperty();

    @Autowired
    public JdbcTeacherDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Teacher teacher) {
        jdbcTemplate.update(properties.getProperty("addTeacher"), teacher.getFirstName(), teacher.getLastNAme(), teacher.getSex()
                , teacher.getAge(), teacher.getDegree(), teacher.getSchedule().getId());
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty("removeTeacherById"), id);
    }

    @Override
    public Teacher findById(int id) throws SQLException {
        return jdbcTemplate.query(properties.getProperty("findTeacherById"),new BeanPropertyRowMapper<>(Teacher.class), id)
                .stream().findAny().orElseThrow(() -> new SQLException("Teacher not found - " + id));
    }

    @Override
    public List<Teacher> findAll() {
        return jdbcTemplate.query(properties.getProperty("findAllTeacher"), new BeanPropertyRowMapper<>(Teacher.class));
    }
    @Override
    public Integer findTeachersCountWithEqualDegree(String degree) {
        return jdbcTemplate.queryForObject(properties.getProperty("findAllTeachersWithEqualDegree"), Integer.class, degree);
    }
}
