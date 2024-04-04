package com.example.myblog.dao;

import com.example.myblog.dto.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostDao{

    public List<Post> getPostList(Map map) throws Exception;

    public int getDataCount(Map map) throws Exception;
}
