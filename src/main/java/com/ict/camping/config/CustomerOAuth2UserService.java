package com.ict.camping.config;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerOAuth2UserService extends DefaultOAuth2UserService{
  //sns 사용자 정보 요청 처리하고 사용자 정보를 수신하는 역할을 한다.(OAuth2User 객체)
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("CustomerOAuth2UserService");

    // 부모클래스의 loadUser 메서드를 호출하여 기본 사용자 정보를 가져온다.
    OAuth2User oAuth2User = super.loadUser(userRequest);
    // 사용자 속성 가져오기
    Map<String, Object> attributes = oAuth2User.getAttributes();
    
    // 제공자를 알 수 있음.(naver, kakao, google)
    String provider = userRequest.getClientRegistration().getRegistrationId();
    if(provider.equals("kakao")){
      // 카카오 아이디 정보 접속
      Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
      if(kakaoAccount == null){
        throw new OAuth2AuthenticationException("Kakao error");
      }
      // 이메일 받기
      String email = (String)kakaoAccount.get("email");

      
      //더 많은 내용을 받으려면 더 깊숙히 들어가야한다.(이름)
      Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

      System.out.println("카카오 정보 : " + kakaoAccount);

      if(properties == null){
        throw new OAuth2AuthenticationException("Kakao error");
      }
      String name = (String) properties.get("nickname");


      return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
        "email", email,
        "name", name,
        //카카오 id는 Long이므로 toString해준다.
        "id", attributes.get("id").toString()
      ), "email");
      


    // 네이버에서 가져오기
    } else if(provider.equals("naver")){
      Map<String, Object> response = (Map<String, Object>) attributes.get("response");
      if(response == null){
        throw new OAuth2AuthenticationException("Naver error");
      }

      String name = (String) response.get("name");
      String email = (String) response.get("email");
      String phone = (String) response.get("phone");
      
      // 필요한 정보를 사용하여 사용자 객체를 생성하거나 반환
      return new DefaultOAuth2User(oAuth2User.getAuthorities(), Map.of(
        "email", email,
        "phone", phone,
        "name", name,
        "id", response.get("id")), "email");

    // 구글에서 가져오기
    } else if(provider.equals("google")) {
      // Google 사용자 정보는 oAuth2User.getAttributes()에서 바로 가져올 수 있습니다.
      Map<String, Object> googleAccount = oAuth2User.getAttributes();
  
      String name = (String) googleAccount.get("name");
      String email = (String) googleAccount.get("email");
      String picture = (String) googleAccount.get("picture"); // 프로필 사진 URL

      System.out.println("구글 정보 : " + googleAccount);
  
      // 필요한 정보를 사용하여 사용자 객체를 생성하거나 반환
      return new DefaultOAuth2User(
          oAuth2User.getAuthorities(),
          Map.of(
              "email", email,
              "name", name,
              "picture", picture, // 추가 정보 저장 가능
              "id", googleAccount.get("sub")
          ),
          "email" // 주로 사용자 정보를 식별할 Key 설정
      );
  }
    return oAuth2User;
  }
}
