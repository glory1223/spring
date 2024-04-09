package com.example.gloryBooks.controller;

import com.example.gloryBooks.dto.GloryUser;
import com.example.gloryBooks.service.GloryUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GloryUserController {

    @Autowired
    GloryUserService gloryUserService;

    @GetMapping(value = "/") //localhost로 접속 '/'생략
    public String index() {
        return "index"; //템플릿 바로 밑에있는걸 찾음
    }

    @GetMapping(value = "/login")
    public String login() {
        return "member/login";
    }

    @PostMapping(value = "/login")
    public String loginGloryUser (GloryUser gloryuser, HttpSession session) { //로그인 처리
        //1. 사용자가 입력한 로그인데이터와 DB에 저장된 데이터가 맞는지 비교.

        try {
            GloryUser loginGloryUser = gloryUserService.loginGloryUser(gloryuser);

            //2. 데이터가 일치하면 (로그인 성공시) index페이지로 이동
            if(loginGloryUser != null) {
                //로그인 성공시 로그인한 사람의 name과 member_id를 세션에 저장
                //.setAttribute(키, 값)
                session.setAttribute("name", loginGloryUser.getName()); //세션에 값을 저장
                session.setAttribute("user_id", loginGloryUser.getUserId()); //세션에 값을 저장

                return "redirect:/";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        return "member/login"; //3. 로그인 실패시 login 페이지로 이동.
    }

    @GetMapping(value = "/logout") //localhost/logout
    public String logoutMember(HttpSession session) {
        //세션에 저장된 name과 member_id 삭제
        session.removeAttribute("name");
        session.removeAttribute("user_id");

        return "redirect:/"; //로그아웃 성공시 index페이지로 redirect
    }


}
