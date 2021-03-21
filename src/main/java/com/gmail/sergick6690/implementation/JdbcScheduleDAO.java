package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.Schedule;
import com.gmail.sergick6690.qeries.ScheduleQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcScheduleDAO implements ScheduleDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ScheduleQueries queries = new ScheduleQueries();
    private BeanPropertyRowMapper<Schedule> rowMapper = new BeanPropertyRowMapper<>(Schedule.class);

    @Override
    public void addSchedule(Schedule schedule) {
        jdbcTemplate.update(queries.getAddSchedule(), schedule.getName());

    }

    @Override
    public Schedule findScheduleByID(int id) {
        return jdbcTemplate.query(queries.getFindScheduleByID(), new Object[]{id}, rowMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Schedule> findAllSchedules() {
        return jdbcTemplate.query(queries.getFindAllSchedules(), rowMapper);
    }

    @Override
    public void removeScheduleById(int id) {
        jdbcTemplate.update(queries.getRemoveScheduleById(), id);
    }
}
