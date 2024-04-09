package com.example.gloryBooks.dao;

import com.example.gloryBooks.dto.GloryUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GloryUserDao {
    public GloryUser loginGloryUser(GloryUser gloryuser) throws Exception;
}
