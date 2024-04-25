package com.board.repository;

import com.board.dto.PostSearchDto;
import com.board.entity.Post;
import com.board.entity.QPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class PostRepositoryCustomImpl implements PostRepositoryCustom{

    private JPAQueryFactory queryFactory;

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("title", searchBy)) { //상품명으로 검색 시
            return QPost.post.title.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)) { //등록자 검색 시
//            return QPost.post.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }





    public PostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<Post> getAdminPostPage(PostSearchDto postSearchDto, Pageable pageable) {
       /*
        select * from post
        where reg_time = ?
        and item_sell_status = ?
        and item_nm(create_by) like '%검색어%'
        order by item_id desc;
         */

        List<Post> content = queryFactory
                .selectFrom(QPost.post)
                .where(searchByLike(postSearchDto.getSearchBy(), postSearchDto.getSearchQuery()))
                .orderBy(QPost.post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        /*
        select count(*) from item
        where reg_time = ?
        and item_sell_status = ?
        and item_nm(create_by) like '%검색어%'
         */
        long total = queryFactory
                .select(Wildcard.count).from(QPost.post)
                .where(
                        searchByLike(postSearchDto.getSearchBy(), postSearchDto.getSearchQuery()))
                .fetchOne();

        //pageable 객체: 한 페이지의 게시물을 보여줄지, 시작 페이지 번호에 대한 정보를 가지고 있따.
        return new PageImpl<>(content, pageable, total);

    }
}
