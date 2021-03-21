package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.SubjectDAO;
import com.gmail.sergick6690.Subject;
import com.gmail.sergick6690.qeries.SubjectQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcSubjectDAO implements SubjectDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SubjectQueries queries = new SubjectQueries();
    private BeanPropertyRowMapper<Subject> rowMapper = new BeanPropertyRowMapper<>(Subject.class);

    @Override
    public void addSubject(Subject subject) {
        jdbcTemplate.update(queries.getAddSubject(), subject.getName(), subject.getDescription());
    }

    @Override
    public Subject findSubjectById(int id) {
        return jdbcTemplate.query(queries.getFindSubjectById(), new Object[]{id}, rowMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Subject> findAllSubjects() {
        return jdbcTemplate.query(queries.getFindAllSubjects(), rowMapper);
    }

    @Override
    public void removeSubjectById(int id) {
        jdbcTemplate.update(queries.getRemoveSubjectById(), id);
    }

    @Override
    public List<Subject> findAllSubjectRelatedToAudience(int id) {
        return jdbcTemplate.query(queries.getFindAllSubjectRelatedToAudience(), new Object[]{id}, rowMapper);
    }
}
