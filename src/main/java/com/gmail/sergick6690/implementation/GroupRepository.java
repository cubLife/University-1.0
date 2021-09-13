package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.GroupDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Group;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = DaoException.class)
public class GroupRepository implements GroupDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/groupQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllGroups";
    private static final String FIND_RELATED_TO_CATHEDRA = "findAllGroupsRelatedToCathedra";

    @Override
    public void add(Group group) throws DaoException {
        try {
            entityManager.persist(group);
        } catch (Exception e) {
            throw new DaoException("Can't add group - " + group, e);
        }
    }

    @Override
    public Group findById(int id) throws DaoException {
        try {
            Group group = entityManager.find(Group.class, id);
            if (group != null) {
                return group;
            } else throw new IllegalArgumentException("Group not found - " + id);
        } catch (Exception e) {
            throw new DaoException("Group not found - " + id, e);
        }
    }

    @Override
    public List<Group> findAll() throws DaoException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Group.class).getResultList();
        } catch (Exception e) {
            throw new DaoException("Can't find any group", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            Group group = findById(id);
            entityManager.remove(group);
        } catch (Exception e) {
            throw new DaoException("Can't remove group with id - " + id, e);
        }
    }

    @Override
    public List<Group> findAllGroupsRelatedToCathedra(int id) throws DaoException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_RELATED_TO_CATHEDRA), Group.class)
                    .setParameter("id", id).getResultList();

        } catch (Exception e) {
            throw new DaoException("Cant find any groups related to cathedra with cathedta id - " + id, e);
        }
    }
}