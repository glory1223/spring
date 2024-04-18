package com.shopmax.entity;

import com.shopmax.constant.Role;
import com.shopmax.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //pk값의 타입은 레퍼런스(참조)타입 Long으로 지정.

    private String name; // String은 사이즈를 지정하지 않으면 디폴트로 varchar(255)타입으로 생성.
    
    @Column(unique = true)
    private String email; // 이메일로 가입받을거라 유니크제약조건걸거임.
    
    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;


    //MemberForDto를 Member 엔티티 객체로 변환 (이러한 함수들은 보통 엔티티클래스에 작성을 합니다.) => 이유: JPA에서는 영속성 컨텍스트에 엔티티 객체를통해 DB에 CRUD를 진행하므로 DTO객체를 반드시 엔티티 객체로 변경시켜줘야한다.
    public static Member createMember(MemberFormDto memberFormDto) {
        Member member = new Member();

        // 사용자가 입력한 회원가입 정보를 member엔티티로 변환.
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setPassword(memberFormDto.getPassword());

        // 개발자가 지정하는 정보.
        member.setRole(Role.USER); // 일반 사용자로 가입한다.
        // member.setRole(Role.ADMIN) // 관리자로 가입한다.

        return member;
    }

}
