package com.example.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping(value = "/") //localhost로 접속 '/'생략
    public String index() {
        return "index"; //템플릿 바로 밑에있는걸 찾음
    }
}
