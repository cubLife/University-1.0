package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.GroupDAO;
import com.gmail.sergick6690.Group;
import com.gmail.sergick6690.qeries.GroupQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcGroupDAO implements GroupDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private BeanPropertyRowMapper<Group> rowMapper = new BeanPropertyRowMapper<>(Group.class);
    private GroupQueries queries = new GroupQueries();

    @Override
    public void addGroup(Group group) {
        jdbcTemplate.update(queries.getAddGroup(), group.getName(), group.getSchedule().getId());
    }

    @Override
    public Group findGroupById(int id) {
        return jdbcTemplate.query(queries.getFindGroupById(), new Object[]{id}, rowMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Group> findAllGroups() {
        return jdbcTemplate.query(queries.getFindAllGroups(), rowMapper);
    }

    @Override
    public void removeGroupById(int id) {
        jdbcTemplate.update(queries.getRemoveGroupById(), id);
    }

    @Override
    public List<Group> findAllGroupsRelatedToCathedra(int id) {
        return jdbcTemplate.query(queries.getFindAllGroupsRelatedToCathedra(), new Object[]{id}, rowMapper);
    }
}
