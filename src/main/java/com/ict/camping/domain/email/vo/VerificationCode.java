package com.ict.camping.domain.email.vo;

import lombok.Data;

@Data
public class VerificationCode {
  private final String email;  // 이메일 주소
  private final String code;   // 인증 코드
  private final long timestamp; // 인증 코드 발급 시간
}