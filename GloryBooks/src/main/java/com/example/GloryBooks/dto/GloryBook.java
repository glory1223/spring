package com.example.gloryBooks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GloryBook {
    private int bookId;
    private String bookName;
    private int bookPrice;
    private String bookAuthor;
    private String bookPublisher;
    private String bookContent;
    private String bookImage;
}
