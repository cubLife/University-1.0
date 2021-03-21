package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.Teacher;
import com.gmail.sergick6690.qeries.TeacherQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTeacherDAO implements TeacherDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private TeacherQueries queries = new TeacherQueries();
    private BeanPropertyRowMapper<Teacher> rowMapper = new BeanPropertyRowMapper<>(Teacher.class);

    @Override
    public void addTeacher(Teacher teacher) {
        jdbcTemplate.update(queries.getAddTeacher(), teacher.getFirstName(), teacher.getLastNAme(), teacher.getSex()
                , teacher.getAge(), teacher.getDegree(), teacher.getSchedule().getId());
    }

    @Override
    public void removeTeacherById(int id) {
        jdbcTemplate.update(queries.getRemoveTeacherById(), id);
    }

    @Override
    public Teacher findTeacherById(int id) {
        return jdbcTemplate.query(queries.getFindTeacherById(), new Object[]{id}, rowMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Teacher> findAllTeacher() {
        return jdbcTemplate.query(queries.getFindTeacherById(), rowMapper);
    }

    @Override
    public Integer findAllTeachersWithEqualDegree(String degree) {
        return jdbcTemplate.queryForObject(queries.getFindAllTeachersWithEqualDegree(), Integer.class, degree);
    }
}
