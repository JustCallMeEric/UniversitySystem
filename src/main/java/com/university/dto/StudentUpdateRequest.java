package com.university.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class StudentUpdateRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;

    @NotNull(message = "Age is required")
    @Positive(message = "Age must be a positive integer")
    private Integer age;

    @NotBlank(message = "College is required")
    @Size(max = 100, message = "College must not exceed 100 characters")
    private String college;

    public StudentUpdateRequest() {
    }

    public StudentUpdateRequest(String name, Integer age, String college) {
        this.name = name;
        this.age = age;
        this.college = college;
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

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
