package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.FacultyDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Faculty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = DaoException.class)
public class FacultyRepository implements FacultyDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/facultyQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllFaculties";

    @Override
    public void add(Faculty faculty) throws DaoException {
        try {
            entityManager.persist(faculty);
        } catch (Exception e) {
            throw new DaoException("Can't add faculty - " + faculty + e, e);
        }
    }

    @Override
    public Faculty findById(int id) throws DaoException {
        try {
            Faculty faculty = entityManager.find(Faculty.class, id);
            if (faculty != null) {
                return faculty;
            } else throw new IllegalArgumentException("Faculty not found - " + id);
        } catch (Exception e) {
            throw new DaoException("Faculty not found - " + id, e);
        }
    }

    @Override
    public List<Faculty> findAll() throws DaoException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Faculty.class).getResultList();
        } catch (Exception e) {
            throw new DaoException("Can't find any faculties", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            Faculty faculty = findById(id);
            entityManager.remove(faculty);
        } catch (Exception e) {
            throw new DaoException("Can't remove faculty with id - " + id, e);
        }
    }
}