package com.example.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping(value = "/") //localhost로 접속 '/'생략
    public String index() {
        return "index"; //템플릿 바로 밑에있는걸 찾음
    }

    @GetMapping(value = "/view") //localhost/view
    public String view() {
        return "post/view";
    }

    @GetMapping(value = "/list") //localhost/list
    public String list() {
        return "post/list";
    }

    @GetMapping(value = "/write") //localhost/write
    public String write() {
        return "post/write";
    }

    @GetMapping(value = "/rewrite") //localhost/rewrite
    public String rewrite() {
        return "post/rewrite";
    }
}


