package com.doczilla.studentmanager.controller;

import com.doczilla.studentmanager.model.Student;
import com.doczilla.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student updatedStudent = studentService.update(student);
        return updatedStudent != null ? ResponseEntity.ok(updatedStudent) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> get(@PathVariable Long id) {
        Optional<Student> student = Optional.ofNullable(studentService.get(id));
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

