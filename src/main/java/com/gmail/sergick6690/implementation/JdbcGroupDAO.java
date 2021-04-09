package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.GroupDAO;
import com.gmail.sergick6690.university.Group;
import com.gmail.sergick6690.PropertyLoader;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
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
    public void add(Group group) {
        jdbcTemplate.update(properties.getProperty(ADD), group.getName(), group.getScheduleId(), group.getCathedraId());
    }

    @Override
    public Group findById(int id) throws NotImplementedException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Group.class), id)
                .stream().findAny().orElseThrow(() -> new NotImplementedException("Group not found - " + id));
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty(REMOVE), id);
    }

    @Override
    public List<Group> findAllGroupsRelatedToCathedra(int id) {
        return jdbcTemplate.query(properties.getProperty(FIND_RELATED_TO_CATHEDRA), new BeanPropertyRowMapper<>(Group.class), id);
    }
}
