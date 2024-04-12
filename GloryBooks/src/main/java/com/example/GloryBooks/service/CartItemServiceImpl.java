package com.example.gloryBooks.service;

import com.example.gloryBooks.dao.CartItemDao;
import com.example.gloryBooks.dto.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CartItemServiceImpl implements CartItemService{

    @Autowired
    CartItemDao cartItemDao;
    @Override
    public void addToCart(CartItem cartItem) {
        cartItemDao.addToCart(cartItem);
    }
}
