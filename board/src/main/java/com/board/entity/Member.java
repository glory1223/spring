package com.board.entity;

import com.board.constant.Role;
import com.board.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email; // 이메일로 가입을 받을것이라 유니크 제약조건 걸것임.

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    //MemberFormDto를 Member 엔티티 객체로 변환. (이러한 함수들은 보통 엔티티클래스에 작성을 합니다.)
    //JPA에서는 영속성컨텍스트에 엔티티 객체를 통해 DB에 CRUD를 진행하므로 DTO객체를 반드시 엔티티 객체로 변경시켜줘야한다.
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        // 패스워드 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());

        Member member = new Member();

        // 사용자가 입력한 회원가입 정보를 member 엔티티로 변환.
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPassword(password); // 사용자가 뷰단에서 입력한 패스워드) // DB에는 최종적으로 암호화된 패스워드가 저장되도록한다.

        // 개발자가 지정하는 정보인 Role.
        member.setRole(Role.USER); // 일반 사용자로 가입한다.
        //member.setRole(Role.ADMIN) // 관리자 사용자로 가입한다.

        return member;
    }
}
