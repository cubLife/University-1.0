package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Faculty;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = RepositoryException.class)
public class JpaFacultyRepository implements com.gmail.sergick6690.Repository.FacultyRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/facultyQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllFaculties";

    @Override
    public void add(Faculty faculty) throws RepositoryException {
        try {
            entityManager.persist(faculty);
        } catch (Exception e) {
            throw new RepositoryException("Can't add faculty - " + faculty + e, e);
        }
    }

    @Override
    public Faculty findById(int id) throws RepositoryException {
        try {
            Faculty faculty = entityManager.find(Faculty.class, id);
            if (faculty != null) {
                return faculty;
            } else throw new IllegalArgumentException("Faculty not found - " + id);
        } catch (Exception e) {
            throw new RepositoryException("Faculty not found - " + id, e);
        }
    }

    @Override
    public List<Faculty> findAll() throws RepositoryException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Faculty.class).getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Can't find any faculties", e);
        }
    }

    @Override
    public void removeById(int id) throws RepositoryException {
        try {
            Faculty faculty = findById(id);
            entityManager.remove(faculty);
        } catch (Exception e) {
            throw new RepositoryException("Can't remove faculty with id - " + id, e);
        }
    }
}