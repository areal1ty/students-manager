package com.doczilla.studentmanager.repository;

import com.doczilla.studentmanager.model.Student;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository {
    Student save(Student student);

    // false if not found
    boolean delete(long id);

    // null if not found
    Student get(long id);

    // null if not found
    List<Student> getAll();

}
