package com.example.myblog.service;

import com.example.myblog.dao.MemberDao;
import com.example.myblog.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    //MemberDao 쓰려면 의존성주입
    @Autowired
    private MemberDao memberDao;


    @Override
    public Member loginMember(Member member) throws Exception {
        return memberDao.loginMember(member) ;
    }
}
