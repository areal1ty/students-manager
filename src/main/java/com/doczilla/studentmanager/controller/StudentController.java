package com.doczilla.studentmanager.controller;

import com.doczilla.studentmanager.model.Student;
import com.doczilla.studentmanager.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        return ResponseEntity.ok(savedStudent);
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student student) {
        Student updatedStudent = studentService.update(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getUserById(@PathVariable Long id) {
        Optional<Student> user = studentService.get(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

