package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.SubjectDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
public class JdbcSubjectDAO implements SubjectDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/subjectQueries.properties").loadProperty();
    private static final String ADD = "addSubject";
    private static final String FIND_BY_ID = "findSubjectById";
    private static final String FIND_ALL = "findAllSubjects";
    private static final String REMOVE = "removeSubjectById";
    private static final String FIND_RELATED_TO_AUDIENCE = "findAllSubjectRelatedToAudience";
    private static final String ASSIGN_TEACHER = "assignTeacher";
    private static final String REMOVE_TEACHER = "removeTeacher";

    @Autowired
    public JdbcSubjectDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Subject subject) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ADD), subject.getName(), subject.getDescription(), subject.getTeacherId());
        } catch (Exception e) {
            throw new DaoException("Can't add subject - " + subject, e);
        }
    }

    @Override
    public Subject findById(int id) throws DaoException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Subject.class), id)
                .stream().findAny().orElseThrow(() -> new DaoException("Subject not found - " + id));
    }

    @Override
    public List<Subject> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Subject.class));
        } catch (Exception e) {
            throw new DaoException("Can't find any subject", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE), id);
        } catch (Exception e) {
            throw new DaoException("Can't remove subject with id - " + id, e);
        }
    }

    @Override
    public List<Subject> findAllSubjectRelatedToAudience(int id) throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_RELATED_TO_AUDIENCE), new BeanPropertyRowMapper<>(Subject.class), id);
        } catch (Exception e) {
            throw new DaoException("Can't find any subjaect related to audience where audience id - " + id, e);
        }
    }

    @Override
    public void assignTeacher(int subjectId, int teacherId) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ASSIGN_TEACHER), teacherId, subjectId);
        } catch (Exception e) {
            throw new DaoException("Can't asign teacher with id - " + teacherId + "for subject with id - " + subjectId, e);
        }
    }

    @Override
    public void removeTeacher(int subjectId) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE_TEACHER), subjectId);
        } catch (Exception e) {
            throw new DaoException("Can't remove teacher from subject where sublect id - " + subjectId, e);
        }
    }
}