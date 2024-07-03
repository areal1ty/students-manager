package com.doczilla.studentmanager.repository;

import com.doczilla.studentmanager.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

@org.springframework.stereotype.Repository
public class StudentRepository implements com.doczilla.studentmanager.repository.Repository {

    private static final BeanPropertyRowMapper<Student> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Student.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertStudent;

    public StudentRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertStudent = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("students")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Student save(Student student) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(student);

        if (student.isNew()) {
            Number newKey = insertStudent.executeAndReturnKey(parameterSource);
            student.setId(newKey.longValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE students SET first_name=:firstName, last_name=:lastName, patronymic=:patronymic, " +
                        "date_of_birth=:dateOfBirth, student_group=:studentGroup WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return student;
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update("DELETE FROM students WHERE id=?", id) != 0;
    }

    @Override
    public Student get(long id) {
        List<Student> students = jdbcTemplate.query("SELECT * FROM students WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(students);
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query("SELECT * FROM students ORDER BY first_name", ROW_MAPPER);
    }
}
