package com.university.dao;

import com.university.entity.College;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollegeMapper {

    College selectById(@Param("id") Long id);
}
