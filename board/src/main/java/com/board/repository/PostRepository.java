package com.board.repository;

import com.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostRepository extends JpaRepository<Post, Long>
        , QuerydslPredicateExecutor<Post>, PostRepositoryCustom {
    // Spring Data JPA 레파지토리에서 사용자 정의 인터페이스 상속.:
    //                                           QuerydslPredicateExecutor<Post>, PostRepositoryCustom

}
