package com.ict.camping.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ict.camping.common.util.JwtUtil;
import com.ict.camping.domain.auth.service.MyUserDetailService;
import com.ict.camping.jwt.JwtRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SecurityConfig {

  private final JwtRequestFilter jwtRequestFilter;
  private final JwtUtil jwtUtil;
  private final MyUserDetailService userDetailService;
  
  public SecurityConfig(JwtRequestFilter jwtRequestFilter, JwtUtil jwtUtil, MyUserDetailService myUserDetailService){
    this.jwtRequestFilter = jwtRequestFilter;   
    this.jwtUtil = jwtUtil;
    this.userDetailService = myUserDetailService;
}

  // 서버에 들어오는 모든 요청은 SecurityFilterChain을 거친다.
  // addFilterBefore때문에 JwtRequestFilter가 먼저 실행된다.


  // 클라이언트에서 http://localhost:8080/oauth2/authorization/kakao 클릭하면
  // SecurityFilter 자동으로 OAuth AuthorizationRedirectFilter가 특정 URL에 오면
  // 자동으로 application.yml에 등록을 보고 자동 처리 

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      log.info("SecurityFilterChain 호출\n");
      http
              .cors(cors -> cors.configurationSource(corsConfigurationSource()))
              .csrf(csrf -> csrf.disable())
              // 요청별 권한 설정
              .authorizeHttpRequests((auth) -> auth
                      .requestMatchers("/api/regular-meetings/hashtags").permitAll()
                      .requestMatchers("/upload/**").permitAll()
                      .requestMatchers("/oauth2/**").permitAll()
                      .requestMatchers("/ws/**", "/topic/**", "/app/**").permitAll()
                      .requestMatchers("/api/regular-meetings").permitAll()
                      .requestMatchers("/api/users/join", "/api/users/login", "/api/users/idCheck", 
                              "/api/signup/sendVerificationEmail", "/api/signup/verifyEmail", "/api/sms/**", "api/users/profile")
                      .permitAll()
                      // 모든 OPTIONS 요청 허용
                      .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                      // 나머지는 인증 필요
                      .anyRequest().authenticated())

              // oauth2 Login 설정
              // successHandler = 로그인 성공 시 호출
              // userInfoEndpoint = 인증 과정에서 인증된 사용자에 대한 정보를 제공하는 API 엔드포인트이다.
              // 사용자 정보를 가져오는 역할을 한다.
              .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService()))  //사용자 정보를 OAuth2 서버에서 가져오는 데 사용됩니다.(CustomOAuth2UserService.loadUser 실행)
                .successHandler(oAuth2AuthenticationSuccessHandler()))                    //사용자가 성공적으로 로그인한 후의 처리 로직을 작성합니다.

                
              .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();
  }


  @Bean
  public CorsConfigurationSource corsConfigurationSource(){
    CorsConfiguration corsConfig = new CorsConfiguration();
    
    //허용할 Origin 설정
    corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
    //허용할 http Method 설정
    corsConfig.setAllowedMethods(Arrays.asList("*"));
    // 허용할 헤더 설정
    corsConfig.setAllowedHeaders(Arrays.asList("*"));
    // 인증정보 허용
    corsConfig.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", corsConfig);
    return source;
  }

  @Bean
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
    return authConfig.getAuthenticationManager();
  }


  //소셜 로그인 성공 시 실행
  @Bean
  OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
      return new OAuth2AuthenticationSuccessHandler(jwtUtil, userDetailService);
  }

  @Bean
  OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
      return new CustomerOAuth2UserService();
  }


  
}