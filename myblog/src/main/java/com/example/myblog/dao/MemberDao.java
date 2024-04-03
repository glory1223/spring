package com.example.myblog.dao;

import com.example.myblog.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper //mybatis에서 쓰려면, 빈객체로 만든다
 public interface MemberDao {
    public Member loginMember(Member member) throws Exception;
}
