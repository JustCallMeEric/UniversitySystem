package com.university.entity;

import java.time.LocalDateTime;

public class Student {
    private Long id;
    private String studentId;
    private String name;
    private Integer age;
    private Long college;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String collegeName;

    public Student() {
    }

    public Student(Long id, String studentId, String name, Integer age, Long college) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.college = college;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getCollege() {
        return college;
    }

    public void setCollege(Long college) {
        this.college = college;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
