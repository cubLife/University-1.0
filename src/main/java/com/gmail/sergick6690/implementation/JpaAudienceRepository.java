package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.Repository.AudienceRepository;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Audience;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = RepositoryException.class)
public class JpaAudienceRepository implements AudienceRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/audienceQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllAudience";

    @Override
    public void add(Audience audience) throws RepositoryException {
        try {
            entityManager.persist(audience);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RepositoryException("Cant't add audience" + e, e);
        }
    }

    @Override
    public Audience findById(int id) throws RepositoryException {
        try {
            Audience audience = entityManager.find(Audience.class, id);
            if (audience != null) {
                return audience;
            } else throw new IllegalArgumentException("Can't find any audience with id - " + id);
        } catch (Exception e) {
            throw new RepositoryException("Can't find any audience with id - " + id, e);
        }
    }

    @Override
    public List<Audience> findAll() throws RepositoryException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Audience.class).getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Can't find any audiences", e);
        }
    }

    @Override
    public void removeById(int id) throws RepositoryException {
        try {
            Audience audience = findById(id);
            entityManager.remove(audience);
        } catch (Exception e) {
            throw new RepositoryException("Can't remove audience with id - " + id, e);
        }
    }
}
