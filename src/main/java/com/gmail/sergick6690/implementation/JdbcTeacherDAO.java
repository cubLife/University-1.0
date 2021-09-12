package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Teacher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = DaoException.class)
public class JdbcTeacherDAO implements TeacherDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/teacherQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllTeacher";
    private static final String FIND_WITH_EQUAL_DEGREE = "findAllTeachersWithEqualDegree";

    @Override
    public void add(Teacher teacher) throws DaoException {
        try {
            entityManager.persist(teacher);
        } catch (Exception e) {
            throw new DaoException("Can't add teacher - " + teacher, e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            Teacher teacher = findById(id);
            entityManager.remove(teacher);
        } catch (Exception e) {
            throw new DaoException("Can't remove teacher with id - " + id, e);
        }
    }

    @Override
    public Teacher findById(int id) throws DaoException {
        try {
            Teacher teacher = entityManager.find(Teacher.class, id);
            if (teacher != null) {
                return teacher;
            } else {
                throw new DaoException("Teacher not found - " + id);
            }
        } catch (Exception e) {
            throw new DaoException("Teacher not found - " + id);
        }
    }

    @Override
    public List<Teacher> findAll() throws DaoException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Teacher.class).getResultList();
        } catch (Exception e) {
            throw new DaoException("Can't find any teacher", e);
        }
    }

    @Override
    public Long findTeachersCountWithEqualDegree(String degree) throws DaoException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_WITH_EQUAL_DEGREE), Long.class)
                    .setParameter("degree", degree)
                    .getSingleResult();
        } catch (Exception e) {
            throw new DaoException("Can't find any teacher with degree - " + degree + e, e);
        }
    }
}