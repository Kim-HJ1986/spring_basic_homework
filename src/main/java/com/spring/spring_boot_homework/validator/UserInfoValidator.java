package com.spring.spring_boot_homework.validator;

import com.spring.spring_boot_homework.dto.UserInfoDto;
import com.spring.spring_boot_homework.model.Users;
import com.spring.spring_boot_homework.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserInfoValidator {
    public static String UserInfoValdation(UserInfoDto requestDto, Optional<Users> found){
//        // 로그인 한 유저 판별
//        if(userDetails != null){
//            throw new IllegalStateException("이미 로그인이 되어있습니다.");
//        }

        //회원 ID 중복확인
        String username = requestDto.getUsername();

        if(username.length() < 3){
            throw new IllegalStateException("아이디는 최소 3자 이상으로 입력하세요.");
        }

        String regex = "^[0-9a-zA-Z]*$";
        if(!username.matches(regex)){
            throw new IllegalStateException("아이디에는 숫자, 영어(소문자 및 대문자)만 포함될 수 있습니다.");
        }

        if(found.isPresent()){
            throw new IllegalStateException("중복된 사용자 ID가 존재합니다.");
        }
        if (!requestDto.getPassword().equals(requestDto.getConfirmPassword())){
            throw new IllegalStateException("비밀번호 확인 값이 다릅니다.");
        }
        if (requestDto.getPassword().length() < 4){
            throw new IllegalStateException("비밀번호는 최소 4자 이상으로 입력해주세요");
        }
        if(requestDto.getPassword().contains(username)){
            throw new IllegalStateException("비밀번호에는 사용자 ID와 같은 값이 포함될 수 없습니다.");
        }

        return username;
    }
}
