package com.spring.spring_boot_homework.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration // 서버가 기동될 때 설정해주겠다.
@EnableWebSecurity // 스프링 시큐리티 지원을 가능하게 한다.
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig{ //extends WebSecurityConfigureAdapter

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(WebSecurity web) {
//        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
//        web
//                .ignoring()
//                .antMatchers("/h2-console/**");
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http
                .csrf().disable()
                .authorizeRequests((authz) -> authz
                        .antMatchers( "/user/login?error" ).permitAll()
                        // image 폴더를 login 없이 허용
                        .antMatchers("/images/**").permitAll()
                        // css 폴더를 login 없이 허용
                        .antMatchers("/css/**").permitAll()
                        // 회원 관리 처리 API 전부를 login 없이 허용
                        .antMatchers("/user/**").permitAll()
                        // 회원 관리 처리 API 전부를 login 없이 허용
                        .antMatchers("/api/replies/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 인증 API 구현부
                .formLogin()
                // 로그인 View 제공 (GET /user/login)
                .loginPage("/user/login")
                // 로그인 처리 (POST /user/login)
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/")
                // 로그인 처리 후 실패 시 URL
                .failureUrl("/user/login?error=")
                .permitAll()
                // 로그인 실패 시 메서드
                .failureHandler(failureHandler())
                .and()
                .logout()
                .logoutUrl("/user/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                // "접근 불가" 페이지 URL 설정
                .accessDeniedPage("/forbidden.html");
        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return new LoginFailHandler();
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        // Get /user/logout 사용 가능하려면 csrf disable()
//        http.csrf().disable();
//        // 인가 API
//        http.authorizeRequests()
//                .antMatchers( "/user/login?error" ).permitAll()
//                // image 폴더를 login 없이 허용
//                .antMatchers("/images/**").permitAll()
//                // css 폴더를 login 없이 허용
//                .antMatchers("/css/**").permitAll()
//                // 회원 관리 처리 API 전부를 login 없이 허용
//                .antMatchers("/user/**").permitAll()
//                // 회원 관리 처리 API 전부를 login 없이 허용
//                .antMatchers("/api/replies/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                    // 인증 API 구현부
//                    .formLogin()
//                    // 로그인 View 제공 (GET /user/login)
//                    .loginPage("/user/login")
//                    // 로그인 처리 (POST /user/login)
//                    .loginProcessingUrl("/user/login")
//                    .defaultSuccessUrl("/")
//                    // 로그인 처리 후 실패 시 URL
//                    .failureUrl("/user/login?error=")
//                    .permitAll()
//                    // 로그인 실패 시 메서드
//                    .failureHandler(failureHandler())
//                    //.permitAll()
//                .and().logout()
//                    .logoutUrl("/user/logout")
//                    .permitAll()
//                .and()
//                    .exceptionHandling()
//                    // "접근 불가" 페이지 URL 설정
//                    .accessDeniedPage("/forbidden.html");
//    }
}
