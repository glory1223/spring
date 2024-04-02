package com.example.springquiz.controller;

import com.example.springquiz.dto.Student;
import com.example.springquiz.service.StudentService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StudentController {

@Autowired
    StudentService studentservice;

@GetMapping("/student")
public List<Student> main() {
        List<Student> list = studentservice.selectList();
        return list;
}

}
