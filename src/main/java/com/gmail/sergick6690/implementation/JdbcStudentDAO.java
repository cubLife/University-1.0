package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class JdbcStudentDAO implements StudentDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/studentQueries.properties").loadProperty();

    @Autowired
    public JdbcStudentDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Student student) {
        jdbcTemplate.update(properties.getProperty("addStudent"), student.getFirstName(), student.getLastNAme(),
                student.getSex(), student.getAge(), student.getCourse());
    }

    @Override
    public Student findById(int id) throws SQLException {
        return jdbcTemplate.query(properties.getProperty("findStudentById"), new BeanPropertyRowMapper<>(Student.class), id)
                .stream().findAny().orElseThrow(() -> new SQLException("Student not found - " + id));
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(properties.getProperty("findAllStudents"), new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty("removeStudentById"), id);
    }
}
