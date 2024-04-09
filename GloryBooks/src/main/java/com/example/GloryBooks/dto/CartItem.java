package com.example.gloryBooks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private int cartitemId;
    private int quantity;
    private int cartId;
    private int bookId;
}
