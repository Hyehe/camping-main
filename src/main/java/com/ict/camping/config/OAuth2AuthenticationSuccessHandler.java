package com.ict.camping.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.ict.camping.common.util.JwtUtil;
import com.ict.camping.domain.auth.service.MyUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// sns로그인 성공시 실행하는 클래스
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
  private final JwtUtil jwtUtil;
  private final MyUserDetailService userDetailService;

    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil, MyUserDetailService userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
    Authentication authentication) throws IOException, ServletException {

    try { 
      System.out.println("SuccessHandler");
        //insteadof = authentication.getPrincipal() = 인증 주체/ 타입이 OAuth2User 인지 확인한다.
        //authentication 객체는 현재 사용자의 인증상태
        //getPrincipal() 가지고 있는 사용자 정보를 반환
      if(authentication.getPrincipal() instanceof OAuth2User){

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String uri = request.getRequestURI();
        String id = "";
        String provider = "";
        System.out.println("oAuth2User" + oAuth2User);

        //id 는 provider마다 받는 방식이 달라서 if 문 안에 작성
        //카카오
        if (uri.contains("kakao")) {
          provider = "kakao";
          id = oAuth2User.getAttribute("id");

        //네이버
        } else if (uri.contains("naver")) {
          provider = "naver";
          id = oAuth2User.getAttribute("id");

        //구글
        } else if (uri.contains("google")) {
          provider = "google";
          id = oAuth2User.getAttribute("id");

        } else {
          provider = "unknown";
        }
        log.info("OAuth user : " + oAuth2User);
        log.info("provider : " + provider);

        //성공 후 토큰 만들어서 클라이언트에게 리다이렉트한다.
        UserDetails userDetails = userDetailService.loadUserByOAuth2User(oAuth2User, provider);


        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String token = jwtUtil.generateToken(id);

        System.out.println("소셜 로그인 토큰 : " + token);
        System.out.println("소셜 로그인 아이디 : " + id);
        // 토큰을 클라이언트에 전달하기 위해 Authorization 헤더로 설정
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Authorization", "Bearer " + token);
        String redirectUrl = String.format(
          //%s => String이 들어간다는 뜻
          "http://localhost:3000/authentication/snsLoginRedirect?token=%s&id=%s&name=%s&email=%s", 
          URLEncoder.encode(token, StandardCharsets.UTF_8),
          URLEncoder.encode(id , StandardCharsets.UTF_8),
          URLEncoder.encode(name, StandardCharsets.UTF_8),
          URLEncoder.encode(email, StandardCharsets.UTF_8)
        );

        // String redirectUrl = "http://localhost:3000";

        System.out.println("id : " + id);
        System.out.println("name : " + name);
        System.out.println("email : " + email);
        System.out.println("token : " + token);

        // 클라이언트에 토큰, 이름, email 등 정보를 가지고 간다.
        response.sendRedirect(redirectUrl);
      }
    } catch (Exception e) {
    e.printStackTrace();
    response.sendRedirect("/login?error");
    }
      
  }
}
