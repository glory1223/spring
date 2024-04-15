package com.example.gloryBooks.dao;

import com.example.gloryBooks.dto.GloryBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GloryBookDao {
    public List<GloryBook> getBookList(Map map) throws Exception;

    public GloryBook getReadBook(int bookId) throws Exception;


    public void insertBook(GloryBook gloryBook) throws Exception;

    public void deleteBook(int bookId) throws Exception;

    public void updateBook(GloryBook gloryBook) throws Exception;
}