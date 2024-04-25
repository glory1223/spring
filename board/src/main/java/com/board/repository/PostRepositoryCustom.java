package com.board.repository;

/*
   query dsl 사용시 3가지 과정.
   1. 사용자정의 인터페이스 구현.
   2. 사용자 정의 인터페이스 작성.
   3. Spring Data JPA 레파지토리에서 사용자 정의 인터페이스 상속.
 */

import com.board.dto.PostSearchDto;
import com.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom { // 사용자 정의 인터페이스 구현.
    Page<Post> getAdminPostPage(PostSearchDto postSearchDto, Pageable pageable);
}