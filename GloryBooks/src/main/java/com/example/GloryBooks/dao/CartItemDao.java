package com.example.gloryBooks.dao;

import com.example.gloryBooks.dto.CartItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartItemDao {

    public  void addToCart(CartItem cartItem);
}
