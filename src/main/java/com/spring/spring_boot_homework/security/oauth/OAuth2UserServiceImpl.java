package com.spring.spring_boot_homework.security.oauth;

import com.spring.spring_boot_homework.model.UserRoleEnum;
import com.spring.spring_boot_homework.model.Users;
import com.spring.spring_boot_homework.repository.UserRepository;
import com.spring.spring_boot_homework.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    //순환참조 걸려있어서 @Lazy로 일단 처리함.. -> 왜??
    // 해결)) WebSecurityConfig Bean 등록 시 OAuth2UserServiceImp Bean을 받아와야하는데
    // 여기에선 WebSecurityConfig의 BCryptPasswordEncoder를 받아와야헤서 순환참조 발생
    @Autowired
    public OAuth2UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    // 구글 로그인 후 구글로 부터 받은 userRequest 데이터에 대해 처리하는 함수.
    // userRequest에는 getClientRegistration, getAccessToken 정보가 들어있다.
    // super.loadUser(userRequest).getAttributes()에는 개인 프로필 정보가 들어있다.
    // username = google_[sub값]
    // password = [암호화(비밀번호임의값)]
    // email = email 그대로
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // registrationId로 어떤 OAuth로 로그인 했는지 확인 가능
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
        System.out.println("getAccessToken: " + userRequest.getAccessToken());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        //구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인을 완료 -> code를 리턴(OAuth-Client라이브러리가 받음) -> Access Token 요청
        //액세스 토큰을 받는 것 까지가 userRequest 정보
        // userReauest 정보 -> loadUser함수 호출 -> 구글로 부터 회원 프로필을 받아준다.
        System.out.println("getAttributes: " + oAuth2User.getAttributes());

        //회원가입을 강제로 진행
        String provider = userRequest.getClientRegistration().getRegistrationId(); //google
        String providerId = oAuth2User.getAttribute("sub");//google의 PK
        String username = provider + "_" + providerId;// google_19146317978241904
        String password = bCryptPasswordEncoder.encode("구글연동테스트");
        String email = oAuth2User.getAttribute("email");
        UserRoleEnum role = UserRoleEnum.USER;

        Users user = userRepository.findByUsername(username).orElse(null);
        if(user == null){
            user = Users.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(user);
        }

        // 시큐리티 세션에는 Authentication 하나만 가지고 있다.
        // 이때 Authentication에는 두 가지 타입을 가질 수 있는데 하나는 UserDetails 다른 하나는 OAuth2User 타입
        // 일반 로그인 시 UserDetailsImpl에서 UserDetails타입을 implements하고 User 객체를 받는 생성자를 만들어줌
        // UserDetailsImpl에서 OAuth2User 타입을 다중 implements 하여 User와 attributes를 받는 생성자 만들어줌
        // 따라서 UserDetailsImpl은 일반 로그인 / OAuth 로그인 둘 다 계정을 만들어주는 역할을 함
        // 정확히는 계정이 아니라 Authentication에 들어갈 UserDetailsImpl을 생성해주는 것.
        // 리턴된 UserDetailsImpl은 Authentication에 담기고 -> Session에 저장된다
        return new UserDetailsImpl(user, oAuth2User.getAttributes());
    }
}
