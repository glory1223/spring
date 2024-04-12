package com.example.gloryBooks.service;



import com.example.gloryBooks.dao.GloryCartDao;
import com.example.gloryBooks.dto.CartItem;
import com.example.gloryBooks.dto.GloryCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GloryCartServiceImpl implements GloryCartService{
    @Autowired
    GloryCartDao gloryCartDao;

    @Override
    public List<GloryCart> cartList(int userId) throws Exception {
        return gloryCartDao.cartList(userId);
    }


}
