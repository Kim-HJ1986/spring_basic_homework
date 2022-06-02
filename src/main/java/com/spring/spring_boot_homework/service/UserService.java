package com.spring.spring_boot_homework.service;

import com.spring.spring_boot_homework.dto.UserInfoDto;
import com.spring.spring_boot_homework.model.UserRoleEnum;
import com.spring.spring_boot_homework.model.Users;
import com.spring.spring_boot_homework.repository.UserRepository;
import com.spring.spring_boot_homework.security.UserDetailsImpl;
import com.spring.spring_boot_homework.validator.UserInfoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    public Users registerUser(UserInfoDto requestDto, UserDetailsImpl userDetails) {

        // 회원가입 정보 Validation check -> OOP 핵심기능의 모듈화 (UserInfoValidator.class)
        Optional<Users> found = userRepository.findByUsername(requestDto.getUsername());
        String username = UserInfoValidator.UserInfoValdation(requestDto, userDetails, found);

        //패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        //사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()){
            if(!requestDto.getAdminToken().equals(ADMIN_TOKEN)){
                throw new IllegalStateException("관리자 토큰값이 틀려 등록이 불가합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        //Users 객체 생성 후 DB 저장
        Users user = new Users(username, password, email, role);
        userRepository.save(user);

        return user;
    }
}
