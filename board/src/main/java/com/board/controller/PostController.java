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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
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


    // 상품 수정페이지 보기
    @GetMapping(value = "/admin/item/{postId}")
    public String postDtl(@PathVariable("postId") Long postId, Model model) {
        try {
            PostFormDto postFormDto = postService.getPostDtl(postId);
            model.addAttribute("postFormDto" , postFormDto);
        } catch(Exception e) {
            model.addAttribute("errorMessage", "게시물정보를 가져오는 도중 에러가 발생했습니다.");

            // 에러발생시 비어있는 객체를 넘겨준다 -> 수정화면에서 postFormDto를 사용하고 있으므로
            model.addAttribute("postFormDto", new PostFormDto());
            return "post/write"; // 등록화면으로 다시 이동.
        }
        return "post/rewrite";
    }






    //상품 수정(update)
    @PostMapping(value = "/admin/item/{postId}")
    public String postUpdate(@Valid PostFormDto postFormDto, Model model, BindingResult bindingResult,
                             //@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,
                             @PathVariable("postId") Long postId) {
        if(bindingResult.hasErrors()) return "post/write"; //유효성 체크에서 걸리면
        PostFormDto getPostFormDto = postService.getPostDtl(postId);
        try {
            postService.updatePost(postFormDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage",
                    "게시물 수정중 에러가 발생했습니다.");
            model.addAttribute("postFormDto", getPostFormDto);
            return "post/rewrite";
        }
        return "redirect:/";
}



    @GetMapping(value = "/post/rewrite")
    private String rewrite() {
        return "post/rewrite";
    }


    // 상품 상세 페이지
    @GetMapping(value = "/post/{postId}")
    public String itemDtl(Model model, @PathVariable("postId") Long postId, Principal principal) {
        PostFormDto postFormDto = postService.getPostDtl(postId);

        model.addAttribute("post", postFormDto);

        model.addAttribute("memberId",principal.getName());
        return "post/detail";
        }


        
        // 게시물 삭제
    @DeleteMapping("/post/{postId}/delete")
    public @ResponseBody ResponseEntity deletePost(@PathVariable("postId") Long postId, Principal principal) {
        // 1. 본인확인
        if(!postService.validatePost(postId, principal.getName())) {
            return new ResponseEntity<String>("주문 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
       //   2. 주문삭제.
        postService.deletePost(postId);

        return new ResponseEntity<Long>(postId, HttpStatus.OK);
        }
    }

