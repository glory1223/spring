package com.example.glory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @GetMapping(value = "/") // localhost/
    public String index() {
        return "index"; // 사이트 메인화면 index.html 띄움.
    }
}
