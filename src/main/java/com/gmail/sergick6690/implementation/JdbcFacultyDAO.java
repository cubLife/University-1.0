package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.Faculty;
import com.gmail.sergick6690.qeries.FacultyQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcFacultyDAO implements FacultyDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private FacultyQueries queries = new FacultyQueries();
    private BeanPropertyRowMapper<Faculty> rowMapper = new BeanPropertyRowMapper<>(Faculty.class);

    @Override
    public void addFaculty(Faculty faculty) {
        jdbcTemplate.update(queries.getAddFaculty(), faculty.getName());
    }

    @Override
    public Faculty findFacultyById(int id) {
        return jdbcTemplate.query(queries.getFindFacultyById(), new Object[]{id}, rowMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Faculty> findAllFaculties() {
        return jdbcTemplate.query(queries.getFindAllFaculties(), rowMapper);
    }

    @Override
    public void removeFacultyById(int id) {
        jdbcTemplate.update(queries.getRemoveFacultyById(), id);
    }
}
