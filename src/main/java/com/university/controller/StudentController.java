package com.university.controller;

import com.university.dto.StudentUpdateRequest;
import com.university.entity.Student;
import com.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable String studentId) {
        Student student = studentService.findByStudentId(studentId);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable String studentId,
            @Valid @RequestBody StudentUpdateRequest request) {
        Student student = new Student();
        student.setStudentId(studentId);
        student.setName(request.getName());
        student.setAge(request.getAge());
        student.setCollege(Long.parseLong(request.getCollege()));

        Student updatedStudent = studentService.update(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String studentId) {
        studentService.delete(studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchByName(@RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("请输入学生姓名");
        }
        List<Student> students = studentService.findByNameContaining(name.trim());
        return ResponseEntity.ok(students);
    }
}
