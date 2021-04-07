package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.GroupDAO;
import com.gmail.sergick6690.university.Group;
import com.gmail.sergick6690.PropertyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class JdbcGroupDAO implements GroupDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/groupQueries.properties").loadProperty();

    @Autowired
    public JdbcGroupDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Group group) {
        jdbcTemplate.update(properties.getProperty("addGroup"), group.getName(), group.getScheduleId(), group.getCathedraId());
    }

    @Override
    public Group findById(int id) throws SQLException {
        return jdbcTemplate.query(properties.getProperty("findGroupById"), new BeanPropertyRowMapper<>(Group.class), id)
                .stream().findAny().orElseThrow(() -> new SQLException("Group not found - " + id));
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query(properties.getProperty("findAllGroups"), new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty("removeGroupById"), id);
    }

    @Override
    public List<Group> findAllGroupsRelatedToCathedra(int id) {
        return jdbcTemplate.query(properties.getProperty("findAllGroupsRelatedToCathedra"), new BeanPropertyRowMapper<>(Group.class), id);
    }
}
