package com.doczilla.studentmanager.service;

import com.doczilla.studentmanager.model.Student;
import com.doczilla.studentmanager.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.doczilla.studentmanager.util.Validation.checkNotFoundWithId;

@Service
public class StudentService {
    private final Repository repository;

    public StudentService(Repository repository) {
        this.repository = repository;
    }

    public Student save(Student student) {
        Assert.notNull(student, "Student must not be null");
        return repository.save(student);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public Student get(Long id) {
        return repository.get(id);
    }

    public List<Student> getAll() {
        return repository.getAll();
    }

    public Student update(Student student) {
        Assert.notNull(student, "Student must not be null");
        return checkNotFoundWithId(repository.save(student), student.getId());
    }
}
