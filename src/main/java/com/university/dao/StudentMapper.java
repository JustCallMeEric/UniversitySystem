package com.university.dao;

import com.university.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {

    List<Student> selectAll();

    Student selectByStudentId(@Param("studentId") String studentId);

    int update(Student student);

    int deleteByStudentId(@Param("studentId") String studentId);
}
