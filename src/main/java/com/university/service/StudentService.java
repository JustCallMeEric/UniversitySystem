package com.university.service;

import com.university.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    Student findByStudentId(String studentId);

    Student update(Student student);

    void delete(String studentId);
}
