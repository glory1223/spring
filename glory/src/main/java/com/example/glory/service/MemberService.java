package com.example.glory.service;

import com.example.glory.entity.Member;
import com.example.glory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //하나의 메소드가 트랜잭션으로 묶인다( DB Exception 혹은 다른 Exception 발생시 롤백.)
@RequiredArgsConstructor // 상수를 의존성주입
public class MemberService  implements UserDetailsService {
    private final MemberRepository memberRepository;

    // 회원가입
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member); // 회원정보 insert후 해당 회원정보 다시 리턴.
    }

    // 회원 중복체크
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());

        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 해당 email 계정을 가진 사용자가 있는지 확인.
        Member member = memberRepository.findByEmail(email); // 이메일로 멤버객체 가져옴.

        if(member == null) { // 사용자가 없다면
            throw new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
