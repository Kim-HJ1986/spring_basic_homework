package com.spring.spring_boot_homework.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {


        if(exception instanceof BadCredentialsException) {
            String errorMessage = exception.getMessage() + ": 아이디 혹은 비밀번호를 확인해주세요.";
            String encodedErrMsg = URLEncoder.encode(errorMessage, "utf-8");
            response.sendRedirect("/user/login?error=" + encodedErrMsg);

            // 아래 방식으로 하다가 6시간이 날아갔습니다 선생님. 아래 방식은 왜 안되는건가요?.....
//            request.setAttribute("LoginFailMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");


//            request.getRequestDispatcher("/user/login?error").forward(request,response);
//            System.out.println(exception.getMessage());

        }
//        else if(exception instanceof AuthenticationServiceException) {
//            request.setAttribute("LoginFailMessage", "죄송합니다. 시스템에 오류가 발생했습니다.");
//        }
//        else if(exception instanceof DisabledException) {
//            request.setAttribute("LoginFailMessage", "현재 사용할 수 없는 계정입니다.");
//        }
//        else if(exception instanceof LockedException) {
//            request.setAttribute("LoginFailMessage", "현재 잠긴 계정입니다.");
//        }
//        else if(exception instanceof AccountExpiredException) {
//            request.setAttribute("LoginFailMessage", "이미 만료된 계정입니다.");
//        }
//        else if(exception instanceof CredentialsExpiredException) {
//            request.setAttribute("LoginFailMessage", "비밀번호가 만료된 계정입니다.");
//        }
//        else request.setAttribute("LoginFailMessage", "계정을 찾을 수 없습니다.");

//        response.sendRedirect("/users/login");


    }
}
