package com.example.gloryBooks.service;

import com.example.gloryBooks.dao.GloryBookDao;
import com.example.gloryBooks.dto.GloryBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class GloryBookServiceImpl implements GloryBookService {
    @Autowired
    GloryBookDao gloryBookDao;


    @Override
    public List<GloryBook> getBookList(Map map) throws Exception {
        return gloryBookDao.getBookList(map);
    }

    @Override
    public GloryBook getReadBook(int bookId) throws Exception {
        return gloryBookDao.getReadBook(bookId);
    }

    @Override
    public void insertBook(GloryBook gloryBook) throws Exception {
        gloryBookDao.insertBook(gloryBook);
    }

    @Override
    public void deleteBook(int bookId) throws Exception {
        gloryBookDao.deleteBook(bookId);
    }

    @Override
    public void updateBook(GloryBook gloryBook) throws Exception {
        gloryBookDao.updateBook(gloryBook);
    }
}
