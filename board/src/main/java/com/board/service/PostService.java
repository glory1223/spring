package com.board.service;

import com.board.dto.PostFormDto;
import com.board.entity.Post;
import com.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

    // post 테이블에 상품등록(insert)
    public Long savePost(PostFormDto postFormDto) throws Exception {

        //1. 게시글 등록 (insert)
        Post post = postFormDto.createPost(); // 엔티티 객체 리턴.
        postRepository.save(post); // insert

        //2. 이미지 등록은 일단 보류.


        return post.getId(); // 게시글 등록 후 게시물ID 리턴.
    }


}
