package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.PropertyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class JdbcCathedraDAO implements CathedraDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/cathedraQueries.properties").loadProperty();

    @Autowired
    public JdbcCathedraDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public void addCathedra(Cathedra cathedra) {
        jdbcTemplate.update(properties.getProperty("addCathedra"), cathedra.getName());

    }

    @Override
    public void removeCathedraById(int id) {
        jdbcTemplate.update(properties.getProperty("removeCathedra"), id);

    }

    @Override
    public Cathedra findCathedraById(int id) throws SQLException {
        return jdbcTemplate.query(properties.getProperty("findCathedraByID"), new Object[]{id}, new BeanPropertyRowMapper<>(Cathedra.class))
                .stream().findAny().orElseThrow(() -> new SQLException("Cathedra not found - " + id));
    }

    @Override
    public List<Cathedra> findAllCathedras() {
        return jdbcTemplate.query(properties.getProperty("findAll"), new BeanPropertyRowMapper<>(Cathedra.class));
    }
}
