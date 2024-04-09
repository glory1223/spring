package com.example.gloryBooks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    private int reviewId;
    private String reviewTitle;
    private String reviewContent;
    private int userId;
    private int bookId;
}
