package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ItemDAO;
import com.gmail.sergick6690.university.Item;
import com.gmail.sergick6690.PropertyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class JdbcItemDAO implements ItemDAO {

    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/itemQueries.properties").loadProperty();

    @Autowired
    public JdbcItemDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Item item) {
        jdbcTemplate.update(properties.getProperty("addItem"), item.getDate(), item.getDuration(), item.getSubject().getId(),
                item.getAudience().getId(), item.getSchedule().getId());

    }

    @Override
    public Item findById(int id) throws SQLException {
        return jdbcTemplate.query(properties.getProperty("findItemById"), new Object[]{id}, new BeanPropertyRowMapper<>(Item.class))
                .stream().findAny().orElseThrow(() -> new SQLException("Item not found - " + id));
    }

    @Override
    public List<Item> findAll() {
        return jdbcTemplate.query(properties.getProperty("findAllItems"), new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty("removeItemsById"), id);
    }
}
