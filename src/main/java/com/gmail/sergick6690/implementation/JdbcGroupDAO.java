package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.GroupDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
public class JdbcGroupDAO implements GroupDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/groupQueries.properties").loadProperty();
    private static final String ADD = "addGroup";
    private static final String FIND_BY_ID = "findGroupById";
    private static final String FIND_ALL = "findAllGroups";
    private static final String REMOVE = "removeGroupById";
    private static final String FIND_RELATED_TO_CATHEDRA = "findAllGroupsRelatedToCathedra";

    @Autowired
    public JdbcGroupDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Group group) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ADD), group.getName(), group.getScheduleId(), group.getCathedraId());
        } catch (Exception e) {
            throw new DaoException("Can't add group - " + group, e);
        }
    }

    @Override
    public Group findById(int id) throws DaoException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Group.class), id)
                .stream().findAny().orElseThrow(() -> new DaoException("Group not found - " + id));
    }

    @Override
    public List<Group> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Group.class));
        } catch (Exception e) {
            throw new DaoException("Can't find any group", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE), id);
        } catch (Exception e) {
            throw new DaoException("Can't remove group with id - " + id, e);
        }
    }

    @Override
    public List<Group> findAllGroupsRelatedToCathedra(int id) throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_RELATED_TO_CATHEDRA), new BeanPropertyRowMapper<>(Group.class), id);
        } catch (Exception e) {
            throw new DaoException("Cant find any groups related to cathedra with cathedta id - " + id, e);
        }
    }
}