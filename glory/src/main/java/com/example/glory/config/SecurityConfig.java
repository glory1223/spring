package com.example.glory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // class안을 Bean객체로 사용하겠다
@EnableWebSecurity // spring security filterChain이 자동으로 포함되는 클래스를 만들어준다.
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //1. 페이지 접근에 관한 설정(authorizeHttpRequests 인증, 인가- 로그인 이후에).
        httpSecurity.authorizeHttpRequests(authorize ->
                        //모든 사용자가 로그인(인증) 없이 접근할 수 있도록 설정.
                        authorize.requestMatchers( "/css/**", "/js/**", "/img/**", "/images/**", "/fonts/**","/library/**").permitAll()
                                .requestMatchers("/", "/member/**", "/item/**").permitAll() // localhost/, localhost/meber/모든하위경로
                                .requestMatchers("/favicon.ico", "/error" ).permitAll()
                                // 관리자만 접근 가능하도록 설정(인가)
                                .requestMatchers("/admin/**").hasRole("ADMIN") // Role이 admin인사람은 /admin/** 즉, 관리자페이지 접근가능.
                                // 그 외의 페이지는 모두 로그인(인증)을 해야 한다.
                                .anyRequest().authenticated() // CustomAuthenticationEntryPoint에 있는 commence() 메소드를 실행.

                )  //2. 로그인에 관한 설정 (체이닝 이어져요)
                .formLogin(formLogin ->
                        formLogin.loginPage("/member/login") // 로그인 페이지 URL
                                .defaultSuccessUrl("/") // 로그인 성공시 이동할 페이지 URL
                                .usernameParameter("email") // ★로그인시 id로 사용할 파라미터 이름 (내 사이트 맞는 걸로 바꿔줘야한다.)
                                .failureUrl("/member/login/error") // 로그인 실패시 이동할 페이지 URL

                ) //3. 로그아웃에 관한 설정 (체이닝 이어져요)
                .logout(logout ->
                                logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃했을때 이동할페이지 따로 controller작업이 필요없음.
                                        .logoutSuccessUrl("/") // 로그아웃 성공시 이동할 Url

                        //4. 인증되지 않은 사용자가 리소스에 접근시 설정
                ).exceptionHandling(handling ->
                        handling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                ).rememberMe(Customizer.withDefaults()); // 로그인 이후 세션을 통해 로그인을 유지

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();
    }
}