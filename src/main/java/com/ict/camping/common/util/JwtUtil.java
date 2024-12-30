package com.ict.camping.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String secret;  // 비밀 키
  @Value("${jwt.expiration}")
  private long expiration;  // 만료시간

  private SecretKey getKey() {
    byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }

    // 토큰 생성
    public String generateToken(String id){
      Map<String, Object> claims = new HashMap<>();
      claims.put("phone", "010-7777-9999");
      return generateToken(id, claims);
    }

  // 토큰 생성
  public String generateToken(String username, Map<String, Object> claims ){
    //내용을 더 추가하고 싶어도 보안 때문에 중요한 정보 넣으면 안됨.
    return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
  }

  private Claims extractAllClaims(String token) {
    SecretKey key = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(secret));
    return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  //토큰을 받아서 추출한다.
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // 토큰 검사
  // UserDetails 유저 정보를 로드하며, 관리하는 역할을 한다. 
  public Boolean validateToken(String token, UserDetails userDetails){
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  //완료 시간 점검
  public Boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

  public Date extractExpiration(String token){
    return extractClaim(token, Claims::getExpiration);
  }


  // 토큰 검증
  public boolean validateToken(String token) {
    try {
        Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
            .build()
            .parseClaimsJws(token);
        return true;
    } catch (JwtException e) {
        return false;
    }
  }

  //여기서 오류 났었음
  // 토큰에서 사용자 ID 추출
public String getUserIdFromToken(String token) {
    try {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        // 토큰이 유효하다면, 사용자 ID를 반환
        return claims.getSubject();
    } catch (ExpiredJwtException e) {
        throw new RuntimeException("토큰이 만료되었습니다.");
    } catch (UnsupportedJwtException e) {
        throw new RuntimeException("지원되지 않는 토큰 형식입니다.");
    } catch (MalformedJwtException e) {
        throw new RuntimeException("잘못된 토큰입니다.");
    } catch (JwtException e) {
        throw new RuntimeException("유효하지 않은 토큰입니다.");
    }
}

// public String getUserIdFromToken(String token) {
//   Claims claims = Jwts.parser()
//           .setSigningKey(secret)
//           .parseClaimsJws(token)
//           .getBody();
//   return claims.getSubject();
// } d오류류

  // 클레임 이름 추출
  // 클레임에서 특정 데이터 추출
  // 모든 클레임 추출
  // 만료 여부 확인
  // 만료 시간 추출


}
