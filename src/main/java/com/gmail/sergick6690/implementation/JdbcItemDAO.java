package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ItemDAO;
import com.gmail.sergick6690.Item;
import com.gmail.sergick6690.qeries.ItemsQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcItemDAO implements ItemDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ItemsQueries queries = new ItemsQueries();
    private BeanPropertyRowMapper<Item> rowMapper = new BeanPropertyRowMapper<>(Item.class);

    @Override
    public void addItem(Item item) {
        jdbcTemplate.update(queries.getAddItem(), item.getDate(), item.getDuration(), item.getSubject().getId(),
                item.getAudience().getId());

    }

    @Override
    public Item findItemById(int id) {
        return jdbcTemplate.query(queries.getFindItemById(), new Object[]{id}, rowMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Item> findAllItems() {
        return jdbcTemplate.query(queries.getFindAllItems(), rowMapper);
    }

    @Override
    public void removeItemsById(int id) {
        jdbcTemplate.update(queries.getRemoveItemsById(), id);
    }
}
