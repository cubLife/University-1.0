package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Faculty;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
public class JdbcFacultyDAO implements FacultyDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/facultyQueries.properties").loadProperty();
    private static final String ADD = "addFaculty";
    private static final String FIND_BY_ID = "findFacultyById";
    private static final String FIND_ALL = "findAllFaculties";
    private static final String REMOVE = "removeFacultyById";

    @Autowired
    public JdbcFacultyDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Faculty faculty) {
        jdbcTemplate.update(properties.getProperty(ADD), faculty.getName());
    }

    @Override
    public Faculty findById(int id) throws NotImplementedException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Faculty.class), id)
                .stream().findAny().orElseThrow(() -> new NotImplementedException("Faculty not found - " + id));
    }

    @Override
    public List<Faculty> findAll() {
        return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Faculty.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty(REMOVE), id);
    }
}
