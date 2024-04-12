package com.example.gloryBooks.service;

import com.example.gloryBooks.dto.GloryCart;

import java.util.List;

public interface GloryCartService {
    List<GloryCart> cartList(int userId) throws Exception;


}
