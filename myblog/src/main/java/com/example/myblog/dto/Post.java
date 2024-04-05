package com.example.myblog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {
    private int postId;
    private String subject;
    private String content;
    private int memberId;
    private String writeDate;
    private String updateDate;
    private int views;
    private String categoryCode;

    private Category category; // Post 객체는 카테고리객체를 가지고있다.
    private Member member;

}