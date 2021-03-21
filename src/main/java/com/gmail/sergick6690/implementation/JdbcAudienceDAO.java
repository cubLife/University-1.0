package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.Audience;
import com.gmail.sergick6690.DAO.AudienceDAO;
import com.gmail.sergick6690.qeries.AudienceQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcAudienceDAO implements AudienceDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AudienceQueries queries = new AudienceQueries();
    private BeanPropertyRowMapper<Audience> rowMapper = new BeanPropertyRowMapper<>(Audience.class);

    @Override
    public void addAudience(Audience audience) {
        jdbcTemplate.update(queries.getAddAudience(), audience.getNumber());

    }

    @Override
    public Audience findAudienceById(int id) {
        return jdbcTemplate.query(queries.getFindAudienceById(), new Object[]{id}, rowMapper)
                .stream().findAny().orElse(new Audience(0, 0));
    }

    @Override
    public List<Audience> findAllAudience() {
        return jdbcTemplate.query(queries.getFindAllAudience(), rowMapper);
    }

    @Override
    public void removeAudienceById(int id) {
        jdbcTemplate.update(queries.getRemoveAudience(), id);
    }
}
