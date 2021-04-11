package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ItemDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Item;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
public class JdbcItemDAO implements ItemDAO {

    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/itemQueries.properties").loadProperty();
    private static final String ADD = "addItem";
    private static final String FIND_BY_ID = "findItemById";
    private static final String FIND_ALL = "findAllItems";
    private static final String REMOVE = "removeItemsById";

    @Autowired
    public JdbcItemDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Item item) {
        jdbcTemplate.update(properties.getProperty(ADD), item.getDate(), item.getDuration(), item.getSubject().getId(),
                item.getAudience().getId(), item.getSchedule().getId());
    }

    @Override
    public Item findById(int id) throws NotImplementedException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Item.class), id)
                .stream().findAny().orElseThrow(() -> new NotImplementedException("Item not found - " + id));
    }

    @Override
    public List<Item> findAll() {
        return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty(REMOVE), id);
    }
}
