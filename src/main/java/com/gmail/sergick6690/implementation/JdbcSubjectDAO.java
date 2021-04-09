package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.SubjectDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Subject;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class JdbcSubjectDAO implements SubjectDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/subjectQueries.properties").loadProperty();
    private static final String ADD = "addSubject";
    private static final String FIND_BY_ID = "findSubjectById";
    private static final String FIND_ALL = "findAllSubjects";
    private static final String REMOVE = "removeSubjectById";
    private static final String FIND_RELATED_TO_AUDIENCE = "findAllSubjectRelatedToAudience";

    @Autowired
    public JdbcSubjectDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Subject subject) {
        jdbcTemplate.update(properties.getProperty(ADD), subject.getName(), subject.getDescription(), subject.getTeacherId());
    }

    @Override
    public Subject findById(int id) throws NotImplementedException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Subject.class), id)
                .stream().findAny().orElseThrow(() -> new NotImplementedException("Subject not found - " + id));
    }

    @Override
    public List<Subject> findAll() {
        return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Subject.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty(REMOVE), id);
    }

    @Override
    public List<Subject> findAllSubjectRelatedToAudience(int id) {
        return jdbcTemplate.query(properties.getProperty(FIND_RELATED_TO_AUDIENCE), new BeanPropertyRowMapper<>(Subject.class), id);
    }
}
