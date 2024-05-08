package com.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearchDto {

    private String searchBy; // 검색 등록자
    private String searchQuery = ""; // 검색내용?

}
