package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Cathedra;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = DaoException.class)
public class JdbcCathedraDAO implements CathedraDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/cathedraQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAll";

    @Override
    public void add(Cathedra cathedra) throws DaoException {
        try {
           entityManager.persist(cathedra);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoException("Cant't add cathedra" + e, e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            Cathedra cathedra = findById(id);
            entityManager.remove(cathedra);
        } catch (Exception e) {
            throw new DaoException("Cant remove cathedra with id - " + id, e);
        }
    }

    @Override
    public Cathedra findById(int id) throws DaoException {
        try {
            Cathedra cathedra = entityManager.find(Cathedra.class, id);
            if (cathedra != null) {
                return cathedra;
            } else throw new DaoException("Cathedra not found - " + id);
        } catch (Exception e) {
            throw new DaoException("Cathedra not found - " + id, e);
        }
    }

    @Override
    public List<Cathedra> findAll() throws DaoException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Cathedra.class).getResultList();
        } catch (Exception e) {
            throw new DaoException("Can't find any cathedras", e);
        }
    }
}
