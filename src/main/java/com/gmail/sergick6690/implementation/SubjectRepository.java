package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.SubjectDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Subject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = DaoException.class)
public class SubjectRepository implements SubjectDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/subjectQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllSubjects";
    private static final String FIND_RELATED_TO_AUDIENCE = "findAllSubjectRelatedToAudience";

    @Override
    public void add(Subject subject) throws DaoException {
        try {
            entityManager.persist(subject);
        } catch (Exception e) {
            throw new DaoException("Can't add subject - " + subject, e);
        }
    }

    @Override
    public Subject findById(int id) throws DaoException {
        try {
            Subject subject = entityManager.find(Subject.class, id);
            if (subject != null) {
                return subject;
            } else {
                throw new IllegalArgumentException("Subject not found - " + id);
            }
        } catch (Exception e) {
            throw new DaoException("Subject not found - " + id);
        }
    }

    @Override
    public List<Subject> findAll() throws DaoException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Subject.class).getResultList();
        } catch (Exception e) {
            throw new DaoException("Can't find any subject", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            Subject subject = findById(id);
            entityManager.remove(subject);
        } catch (Exception e) {
            throw new DaoException("Can't remove subject with id - " + id, e);
        }
    }

    @Override
    public List<Subject> findAllSubjectRelatedToAudience(int id) throws DaoException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_RELATED_TO_AUDIENCE), Subject.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            throw new DaoException("Can't find any subjaect related to audience where audience id - " + id, e);
        }
    }
}