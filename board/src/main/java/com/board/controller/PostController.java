package com.board.controller;

import com.board.dto.PostFormDto;
import com.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    @GetMapping(value = "/posts/list")
    public String list() {
        return "post/list";
    }

    @GetMapping(value = "/admin/item/new")
    public String write(Model model) {
        model.addAttribute("postFormDto", new PostFormDto());
        return "post/write";
    }


    // 게시물 등록 처리(insert)
    @PostMapping(value = "/admin/item/new")
    public String write(@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()) return "post/write"; // 유효성 체크에서 걸리면.
        try {
            postService.savePost(postFormDto);
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errorMessage","게시물 등록중 에러가 발생했습니다.");
            return "post/write";
        }
        return "redirect:/post/list"; // 상품등록이 문제없이 완료되면 게시물 목록 화면으로 이동.
    }











    @GetMapping(value = "/posts/rewrite")
    private String rewrite() {
        return "post/rewrite";
    }

    @GetMapping(value = "/posts/detail")
    private String detail() {
        return "post/detail";
    }
}
