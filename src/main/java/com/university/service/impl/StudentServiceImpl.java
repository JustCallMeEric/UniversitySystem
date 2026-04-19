package com.university.service.impl;

import com.university.controller.GlobalExceptionHandler.StudentNotFoundException;
import com.university.dao.CollegeMapper;
import com.university.dao.StudentMapper;
import com.university.entity.College;
import com.university.entity.Student;
import com.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final CollegeMapper collegeMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper, CollegeMapper collegeMapper) {
        this.studentMapper = studentMapper;
        this.collegeMapper = collegeMapper;
    }

    @Override
    public List<Student> findAll() {
        return studentMapper.selectAll();
    }

    @Override
    public Student findByStudentId(String studentId) {
        Student student = studentMapper.selectByStudentId(studentId);
        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + studentId + " does not exist");
        }
        College college = collegeMapper.selectById(student.getCollege());
        if (college == null) {
            student.setCollegeName("未查询到该学院");
        } else {
            student.setCollegeName(college.getName());
        }
        return student;
    }

    @Override
    public Student update(Student student) {
        Student existingStudent = studentMapper.selectByStudentId(student.getStudentId());
        if (existingStudent == null) {
            throw new StudentNotFoundException("Student with ID " + student.getStudentId() + " does not exist");
        }

        int rowsAffected = studentMapper.update(student);
        if (rowsAffected == 0) {
            throw new StudentNotFoundException("Failed to update student with ID " + student.getStudentId());
        }

        return studentMapper.selectByStudentId(student.getStudentId());
    }

    @Override
    public void delete(String studentId) {
        Student existingStudent = studentMapper.selectByStudentId(studentId);
        if (existingStudent == null) {
            throw new StudentNotFoundException("Student with ID " + studentId + " does not exist");
        }

        int rowsAffected = studentMapper.deleteByStudentId(studentId);
        if (rowsAffected == 0) {
            throw new StudentNotFoundException("Failed to delete student with ID " + studentId);
        }
    }

    @Override
    public List<Student> findByNameContaining(String name) {
        return studentMapper.selectByNameContaining(name);
    }
}
