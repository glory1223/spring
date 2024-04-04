package com.example.myblog.service;

import com.example.myblog.dto.Post;

import java.util.List;
import java.util.Map;

public interface PostService {

    public List<Post> getPostList(Map map) throws Exception;

    public int getDataCount(Map map) throws Exception;
}

