package com.board.controller;

import com.board.dto.PostFormDto;
import com.board.dto.PostSearchDto;
import com.board.entity.Post;
import com.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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
        return "redirect:/"; // 상품등록이 문제없이 완료되면 게시물 목록 화면으로 이동.
    }

// 게시물 전체 리스트
//상품 전체 리스트
@GetMapping(value = {"/admin/items", "/admin/items/{page}"}) // (/admin/items, 페이징처리할때 두번)
public String postList(Model model, PostSearchDto postSearchDto,
                       @PathVariable(value = "page") Optional<Integer> page) {
    // 한페이지 당 6개의 게시물 보여줌.
    Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
    Page<Post> posts = postService.getAdminPostPage(postSearchDto, pageable);

    model.addAttribute("posts", posts);
    model.addAttribute("postSearchDto", postSearchDto);
    model.addAttribute("maxPage", 5);

    return "post/list";
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
