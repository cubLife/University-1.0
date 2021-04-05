package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.university.Audience;
import com.gmail.sergick6690.DAO.AudienceDAO;
import com.gmail.sergick6690.PropertyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class JdbcAudienceDAO implements AudienceDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/audienceQueries.properties").loadProperty();

    @Autowired
    public JdbcAudienceDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Audience audience) {
        jdbcTemplate.update(properties.getProperty("addAudience"), audience.getNumber());

    }

    @Override
    public Audience findById(int id) throws SQLException {
        return jdbcTemplate.query(properties.getProperty("findAudienceById"), new Object[]{id}, new BeanPropertyRowMapper<>(Audience.class))
                .stream().findAny().orElseThrow(() -> new SQLException("Audience not found - " + id));
    }

    @Override
    public List<Audience> findAll() {
        return jdbcTemplate.query(properties.getProperty("findAllAudience"), new BeanPropertyRowMapper<>(Audience.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty("removeAudience"), id);
    }

}
