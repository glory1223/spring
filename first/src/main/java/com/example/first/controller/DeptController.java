package com.example.first.controller;


import com.example.first.dto.Dept;
import com.example.first.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //해당 클래스를 컨트롤러로 사용하겟다는 의미 또는 @Controller
public class DeptController {

 @Autowired
 DeptService deptService; //DeptService deptService = new DeptServiceImpl();


 //@PostMapping //post 방식으로 올때 해당메소드를 실행
 @GetMapping("/main") //get방식으로 요청이 올때 해당 메소드를 실행
 public List<Dept> main() {
     List<Dept> list = deptService.selectList();
     return list; //리턴해 주는 값을 Response body에 담아서 view에전달
 }
}
