package com.example.gloryBooks.service;

import com.example.gloryBooks.dto.GloryBook;

import java.util.List;
import java.util.Map;

public interface GloryBookService {
    public List<GloryBook> getBookList(Map map) throws Exception;

    public GloryBook getReadBook(int bookId) throws Exception;


    public void insertBook(GloryBook gloryBook) throws Exception;

    public void deleteBook(int bookId) throws Exception;

    public void updateBook(GloryBook gloryBook) throws Exception;
}
