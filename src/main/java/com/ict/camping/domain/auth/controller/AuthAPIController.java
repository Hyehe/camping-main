package com.ict.camping.domain.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.camping.common.util.JwtUtil;
import com.ict.camping.domain.auth.vo.DataVO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthAPIController {

  @Autowired
  private JwtUtil jwtUtil;

  @GetMapping("/test")
  public String hello() {
    return "hello";
  }
  
  @PostMapping("/generate-token")
  public String generateToken(@RequestBody Map<String, String> request) {

    //클라이언트가 username이라는 key에 정보를 보냈다고 가정
    String username = request.get("username");
    System.out.println("username" + username);

    // 더 많은 정보를 추가할 수 있다. 
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", "USER");

    return jwtUtil.generateToken(username, claims);
  }

  @PostMapping("/validate-token")
  public DataVO validateToken(@RequestBody Map<String, String> request) {
    String token = request.get("token");
    log.info("token : ", token);
      return null;
  }
  
}
