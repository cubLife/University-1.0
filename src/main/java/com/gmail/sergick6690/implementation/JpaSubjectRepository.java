package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Subject;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = RepositoryException.class)
public class JpaSubjectRepository implements com.gmail.sergick6690.Repository.SubjectRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/subjectQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllSubjects";
    private static final String FIND_RELATED_TO_AUDIENCE = "findAllSubjectRelatedToAudience";

    @Override
    public void add(Subject subject) throws RepositoryException {
        try {
            entityManager.persist(subject);
        } catch (Exception e) {
            throw new RepositoryException("Can't add subject - " + subject, e);
        }
    }

    @Override
    public Subject findById(int id) throws RepositoryException {
        try {
            Subject subject = entityManager.find(Subject.class, id);
            if (subject != null) {
                return subject;
            } else {
                throw new IllegalArgumentException("Subject not found - " + id);
            }
        } catch (Exception e) {
            throw new RepositoryException("Subject not found - " + id);
        }
    }

    @Override
    public List<Subject> findAll() throws RepositoryException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Subject.class).getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Can't find any subject", e);
        }
    }

    @Override
    public void removeById(int id) throws RepositoryException {
        try {
            Subject subject = findById(id);
            entityManager.remove(subject);
        } catch (Exception e) {
            throw new RepositoryException("Can't remove subject with id - " + id, e);
        }
    }

    @Override
    public List<Subject> findAllSubjectRelatedToAudience(int id) throws RepositoryException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_RELATED_TO_AUDIENCE), Subject.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Can't find any subjaect related to audience where audience id - " + id, e);
        }
    }
}