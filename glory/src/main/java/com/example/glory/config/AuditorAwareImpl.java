package com.example.glory.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// 로그인한 사용자의 정보를 등록자와 수정자로 지정하기 위해 사용한다.
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() { // JPA Auditor에서 해당 userId를 등록자와 수정자로 지정.
        // 로그인한 사용자의 정보를 가지고 온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //context에있는 Authentication 객체를 가져옴.
        String userId = "";

        if(authentication != null) {
            userId = authentication.getName(); // 로그인한 사용자의 id(우리의경우 email)을 가지고 온다.
        }

        return Optional.of(userId); // JPA Auditor에서 해당 userId를 등록자와 수정자로 지정.
    }
}
