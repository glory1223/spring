package com.board.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // 클래스내부에 있는 객체들을 빈객체로 사용할수있게한다.
@EnableJpaAuditing // JPA의 auditing 기능을 활성화 시키는 어노테이션.
public class AuditConfig {

    @Bean // 이제 주도권은 스프링한테 있다. Auditor기능을.
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
