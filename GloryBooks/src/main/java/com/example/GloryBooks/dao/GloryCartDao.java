package com.example.gloryBooks.dao;

import com.example.gloryBooks.dto.GloryCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GloryCartDao {
    List<GloryCart> cartList(int userId) throws Exception;


}
