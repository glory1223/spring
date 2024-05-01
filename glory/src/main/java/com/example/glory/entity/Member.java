package com.example.glory.entity;

import com.example.glory.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name= "member")
@Getter
@Setter
@ToString
public class Member  extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //pk값의 타입은 레퍼런스(참조)타입 Long으로 지정.

    private String name; // String은 사이즈를 지정하지 않으면 디폴트로 varchar(255)타입으로 생성.

    @Column(unique = true)
    private String email; // 이메일로 가입받을거라 유니크제약조건걸거임.

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

}