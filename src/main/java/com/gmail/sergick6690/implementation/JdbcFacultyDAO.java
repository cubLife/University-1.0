package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Faculty;
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
    public void add(Faculty faculty) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ADD), faculty.getName());
        } catch (Exception e) {
            throw new DaoException("Can't add faculty - " + faculty, e);
        }
    }

    @Override
    public Faculty findById(int id) throws DaoException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Faculty.class), id)
                .stream().findAny().orElseThrow(() -> new DaoException("Faculty not found - " + id));
    }

    @Override
    public List<Faculty> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Faculty.class));
        } catch (Exception e) {
            throw new DaoException("Can't find any faculties", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE), id);
        } catch (Exception e) {
            throw new DaoException("Can't remove faculty with id - " + id, e);
        }
    }
}