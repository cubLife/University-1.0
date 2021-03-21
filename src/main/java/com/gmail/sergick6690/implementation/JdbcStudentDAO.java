package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.StudentDAO;
import com.gmail.sergick6690.Student;
import com.gmail.sergick6690.qeries.StudentQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcStudentDAO implements StudentDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private StudentQueries queries = new StudentQueries();
    private BeanPropertyRowMapper<Student> rowMapper = new BeanPropertyRowMapper<>(Student.class);

    @Override
    public void addStudent(Student student) {
        jdbcTemplate.update(queries.getAddStudent(), student.getFirstName(), student.getLastNAme()
                , student.getSex(), student.getAge(), student.getCourse());
    }

    @Override
    public Student findStudentById(int id) {
        return jdbcTemplate.query(queries.getFindStudentById(), new Object[]{id}, rowMapper)
                .stream().findAny().orElse(null);
    }

    @Override
    public List<Student> findAllStudents() {
        return jdbcTemplate.query(queries.getFindAllStudents(), rowMapper
        );
    }

    @Override
    public void removeStudentById(int id) {
        jdbcTemplate.update(queries.getRemoveStudentById(), id);

    }
}
