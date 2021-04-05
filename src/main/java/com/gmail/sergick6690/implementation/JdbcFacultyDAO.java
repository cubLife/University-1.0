package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.university.Faculty;
import com.gmail.sergick6690.PropertyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class JdbcFacultyDAO implements FacultyDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/facultyQueries.properties").loadProperty();

    @Autowired
    public JdbcFacultyDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Faculty faculty) {
        jdbcTemplate.update(properties.getProperty("addFaculty"), faculty.getName());
    }

    @Override
    public Faculty findById(int id) throws SQLException {
        return jdbcTemplate.query(properties.getProperty("findFacultyById"), new Object[]{id}, new BeanPropertyRowMapper<>(Faculty.class))
                .stream().findAny().orElseThrow(() -> new SQLException("Faculty not found - " + id));
    }

    @Override
    public List<Faculty> findAll() {
        return jdbcTemplate.query(properties.getProperty("findAllFaculties"), new BeanPropertyRowMapper<>(Faculty.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty("removeFacultyById"), id);
    }
}
