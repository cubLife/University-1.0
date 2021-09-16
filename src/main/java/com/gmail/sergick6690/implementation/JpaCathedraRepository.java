package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Cathedra;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = RepositoryException.class)
public class JpaCathedraRepository implements com.gmail.sergick6690.Repository.CathedraRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/cathedraQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAll";

    @Override
    public void add(Cathedra cathedra) throws RepositoryException {
        try {
            entityManager.persist(cathedra);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RepositoryException("Cant't add cathedra" + e, e);
        }
    }

    @Override
    public void removeById(int id) throws RepositoryException {
        try {
            Cathedra cathedra = findById(id);
            entityManager.remove(cathedra);
        } catch (Exception e) {
            throw new RepositoryException("Cant remove cathedra with id - " + id, e);
        }
    }

    @Override
    public Cathedra findById(int id) throws RepositoryException {
        try {
            Cathedra cathedra = entityManager.find(Cathedra.class, id);
            if (cathedra != null) {
                return cathedra;
            } else throw new IllegalArgumentException("Cathedra not found - " + id);
        } catch (Exception e) {
            throw new RepositoryException("Cathedra not found - " + id, e);
        }
    }

    @Override
    public List<Cathedra> findAll() throws RepositoryException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Cathedra.class).getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Can't find any cathedras", e);
        }
    }
}
