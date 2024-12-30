package com.ict.camping.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ict.camping.common.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// 토큰을 추출하고, 유효성 검사하여, 유효한 경우만 사용자 정보 로드
// 보호된 리소스에 대한 접근이 가능한 사용자인지 확인

// 요청 헤더에 토큰이 있는지 없으면 맞는 토큰인지 검사하는 역할
@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter{
  @Autowired
  private JwtUtil jwtUtil;

   // Spring Security가 UserDetailsService를 호출하여 사용자 정보를 데이터베이스에서 가져오는 역할
  @Autowired
  private UserDetailsService userDetailsService;

  // 요청 헤더에서 Authorization 값 확인
  // 값이 없거나 'Bearer' 로 시작하지 않으면 로그를 남기고 다음 필터로 전달된다. 
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

        // System.out.println("JwtRequestFilter");

      final String requestTokenHeader = request.getHeader("Authorization");
      String username = null;
      String jwtToken = null;



      //토큰이 있으며 Authorization 한에 "Bearer " 로 시작(띄어쓰기 중요)
      if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

        //토큰 추출
        jwtToken = requestTokenHeader.substring(7);
        try {
          // 이름추출 (id) 오류 발생
          username = jwtUtil.extractUsername(jwtToken);

        } catch (Exception e) {
          System.out.println("JWT token 오류");
          logger.warn("jwtToken error");
          log.warn("JWT token 오류: {}", e.getMessage());
        }
      } else {
        System.out.println("JWT token 없음");
        logger.warn("jwtToken empty");
        log.warn("JWT token 없음 또는 Bearer로 시작하지 않음");
      }
  

    //사용자 이름(아이디) 추출, 현재 SecurityContext에 인증 정보가 없는지 확인
    if(username !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
      //사용자 이름을 가지고 현재 DB에 있는지 검사
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      log.info("userDetails.username : " + userDetails.getUsername() + "\n");
      log.info("userDetails.password : " + userDetails.getPassword() + "\n");

      if (jwtUtil.validateToken(jwtToken, userDetails)) {
        // Spring Security 인증 객체 생성
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities()
        );

        // Spring Security 인증 객체 추가 세부 정보를 성정
        usernamePasswordAuthenticationToken
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Spring Security 컨텍스트에 저장
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      } else {
        log.warn("JWR 토큰이 유효하지 않습니다.");
      }
    }
    filterChain.doFilter(request, response);
  }
}
