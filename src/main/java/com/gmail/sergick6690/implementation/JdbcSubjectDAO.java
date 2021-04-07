package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.SubjectDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class JdbcSubjectDAO implements SubjectDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/subjectQueries.properties").loadProperty();

    @Autowired
    public JdbcSubjectDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Subject subject) {
        jdbcTemplate.update(properties.getProperty("addSubject"), subject.getName(), subject.getDescription(), subject.getTeacherId());
    }

    @Override
    public Subject findById(int id) throws SQLException {
        return jdbcTemplate.query(properties.getProperty("findSubjectById"), new BeanPropertyRowMapper<>(Subject.class), id)
                .stream().findAny().orElseThrow(() -> new SQLException("Subject not found - " + id));
    }

    @Override
    public List<Subject> findAll() {
        return jdbcTemplate.query(properties.getProperty("findAllSubjects"), new BeanPropertyRowMapper<>(Subject.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty("removeSubjectById"), id);
    }

    @Override
    public List<Subject> findAllSubjectRelatedToAudience(int id) {
        return jdbcTemplate.query(properties.getProperty("findAllSubjectRelatedToAudience"), new BeanPropertyRowMapper<>(Subject.class), id);
    }
}
