package com.shopmax.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto { //내가 전송할때 필요한 데이터만 쓰면됨.

    private String name;

    private String email;

    private String password;

    private String address;
}
