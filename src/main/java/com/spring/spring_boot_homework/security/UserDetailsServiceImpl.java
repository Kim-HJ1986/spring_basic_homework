package com.spring.spring_boot_homework.security;

import com.spring.spring_boot_homework.model.Users;
import com.spring.spring_boot_homework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 session( Authentication( UserDetails))  -> 전체적인 구조 (포함관계)
// 이 곳은 세션에 저장될 Authentication에 넣어줄 UserDetails(user)를 생성 후 넣어주는 곳이다.
// 즉, loadUserByUsername 메서드의 리턴은 Authentication으로 가게된다.
// 시큐리티 설정에서(SecurityWebConfig) loginProcessingUrl("/user/login")
// 으로 로그인 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행된다.
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // 로그인 요청시 AuthenticationManager의 요청에 의해 username에 해당하는 User가 있는지 확인 후 리턴해준다.
    // 로그인 폼 방식에서 인풋의 name이 username이라서 아래 메서드의 파라미터 명이 username이 된 것. (둘이 같아야함)
    // 만약 다른 값으로 설정해주고 싶다면 WebSecurityConfig에서 .usernameParameter()를 설정해줘야한다.
    // 아래 함수 실행 결과로 @AuthenticationPrincipal 어노테이션이 만들어진다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));

        return new UserDetailsImpl(user);
    }
}
