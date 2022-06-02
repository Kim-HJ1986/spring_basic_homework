package com.spring.spring_boot_homework.security;

import com.spring.spring_boot_homework.model.UserRoleEnum;
import com.spring.spring_boot_homework.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserDetailsImpl implements UserDetails, OAuth2User {

    private final Users user;
    private Map<String, Object> attributes;

    //일반 로그인 할 때 사용하는 생성자
    public UserDetailsImpl(Users user){
        this.user = user;
    }

    //OAuth 로그인 할 때 사용하는 생성자
   public UserDetailsImpl(Users user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    public Users getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 만약 유저가 1년동안 로그인을 하지 않았을 때 휴면계정 처리를 하고싶다면?
        // 현재 시간 - user.getLoginDate() > 1year 이면 return true; 와 같이 코드를 작성하면 된다.
        return true;
    }

    @Override // 이곳에서 회원의 권한을 담아준다.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = user.getRole();
        String authority = role.getAuthority(); //"ROLE_USER" / "ROLE_ADMIN"

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority); //"ROLE_" 형식으로 줘야함
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;

    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
