package com.spring.spring_boot_homework.service;

import com.spring.spring_boot_homework.dto.UserInfoDto;
import com.spring.spring_boot_homework.model.Users;
import com.spring.spring_boot_homework.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 정보 validation check _ 닉네임이 올바르지 않습니다.")
    void userValidation_Normal(){

        //given
        String username1 = "rlafbf";
        String username2 = "정국이";
        String username3 = "rl";
        String password1 = "1234";
        String password2 = "123";
        String password3 = "rlafbf3123";
        String confirmPassword1 = "1234";
        String confirmPassword2 = "123";
        String confirmPassword3 = "rlafbf3123";
        String email1 = "rlafbf1@naver.com";
        String email2= "rlafbf2@naver.com";
        String email3 = "rlafbf3@naver.com";
        String email4 = "rlafbf4@naver.com";
        String email5 = "rlafbf5@naver.com";
        String email6 = "rlafbf6@naver.com";
        String email7 = "rlafbf7@naver.com";
        boolean admin = false;
        String adminToken = "";

        //정상 케이스
        UserInfoDto userInfoDto1 = new UserInfoDto(username1, password1, confirmPassword1, email1, admin, adminToken);
        //오류 케이스: 닉네임 2자 이하
        UserInfoDto userInfoDto2 = new UserInfoDto(username3, password1, confirmPassword1, email2, admin, adminToken);
        //오류 케이스: 영어,숫자 외 다른 값이 포함된 닉네임
        UserInfoDto userInfoDto3 = new UserInfoDto(username2, password1, confirmPassword1, email3, admin, adminToken);
        //오류 케이스: 비밀번호 3자 이하
        UserInfoDto userInfoDto4 = new UserInfoDto(username1 + "1", password2, confirmPassword2, email4, admin, adminToken);
        //오류 케이스: 비밀번호 불일치
        UserInfoDto userInfoDto5 = new UserInfoDto(username1 +"2", password1, confirmPassword2, email5, admin, adminToken);
        //오류 케이스: 비밀번호에 닉네임 포함
        UserInfoDto userInfoDto6 = new UserInfoDto(username1 +"3", password3, confirmPassword3, email6, admin, adminToken);
        //오류 케이스: 중복된 닉네임 테스트
        UserInfoDto userInfoDto7 = new UserInfoDto(username1, password1, confirmPassword1, email7, admin, adminToken);

        UserService userService = new UserService(passwordEncoder, userRepository);

        //when
        Users user1 = userService.registerUser(userInfoDto1, userDetails);

        Exception exception2 = assertThrows(IllegalStateException.class, () -> {
            userService.registerUser(userInfoDto2, userDetails);
        });
        Exception exception3 = assertThrows(IllegalStateException.class, () -> {
            userService.registerUser(userInfoDto3, userDetails);
        });
        Exception exception4 = assertThrows(IllegalStateException.class, () -> {
            userService.registerUser(userInfoDto4, userDetails);
        });
        Exception exception5 = assertThrows(IllegalStateException.class, () -> {
            userService.registerUser(userInfoDto5, userDetails);
        });
        Exception exception6 = assertThrows(IllegalStateException.class, () -> {
            userService.registerUser(userInfoDto6, userDetails);
        });

        //then
        assert(user1 != null);
        assertEquals("아이디는 최소 3자 이상으로 입력하세요.", exception2.getMessage());
        assertEquals("아이디에는 숫자, 영어(소문자 및 대문자)만 포함될 수 있습니다.", exception3.getMessage());
        assertEquals("비밀번호는 최소 4자 이상으로 입력해주세요", exception4.getMessage());
        assertEquals("비밀번호 확인 값이 다릅니다.", exception5.getMessage());
        assertEquals("비밀번호에는 사용자 ID와 같은 값이 포함될 수 없습니다.", exception6.getMessage());

        if(userRepository.findByUsername(userInfoDto7.getUsername()).isPresent()){
            System.out.println("중복된 닉네임 입니다.");
        }
    }
}
