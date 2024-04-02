package com.example.springquiz.dao;

import com.example.springquiz.dto.Student;
import com.example.springquiz.service.StudentService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface StudentDao {

    public List<Student> selectList();

}
