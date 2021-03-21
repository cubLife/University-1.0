package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.Cathedra;
import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.qeries.CathedraQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcCathedraDAO implements CathedraDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CathedraQueries queries= new CathedraQueries();
    private BeanPropertyRowMapper<Cathedra> rowMapper= new BeanPropertyRowMapper<>(Cathedra.class);

    @Override
    public void addCathedra(Cathedra cathedra) {
        jdbcTemplate.update(queries.getAddCathedra(),cathedra.getName());

    }

    @Override
    public void removeCathedraById(int id) {
        jdbcTemplate.update(queries.getRemoveCathedraById(),id);

    }

    @Override
    public Cathedra findCathedraById(int id) {
        return jdbcTemplate.query(queries.getFindCathedraById(),new Object []{id}, rowMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Cathedra> findAllCathedras() {
        return jdbcTemplate.query(queries.getFindAllCathedras(),new BeanPropertyRowMapper<>(Cathedra.class));
    }
}
