package com.example.gloryBooks.service;

import com.example.gloryBooks.dao.GloryUserDao;
import com.example.gloryBooks.dto.GloryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GloryUserServiceImpl implements GloryUserService{
    @Autowired
    private GloryUserDao gloryUserDao;


    @Override
    public GloryUser loginGloryUser(GloryUser gloryuser) throws Exception {
        return gloryUserDao.loginGloryUser(gloryuser);
    }
}
