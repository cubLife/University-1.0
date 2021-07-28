package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ItemDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Item;
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
    public void add(Item item) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ADD), item.getDay(), item.getHour(), item.getDuration(), item.getSubjectId(),
                    item.getAudienceId(), item.getScheduleId());
        } catch (Exception e) {
            throw new DaoException("Can't add item - " + item, e);
        }
    }

    @Override
    public Item findById(int id) throws DaoException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Item.class), id)
                .stream().findAny().orElseThrow(() -> new DaoException("Item not found - " + id));
    }

    @Override
    public List<Item> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Item.class));
        } catch (Exception e) {
            throw new DaoException("Can't find any items", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE), id);
        } catch (Exception e) {
            throw new DaoException("Can't remove item with id - " + id, e);
        }
    }
}