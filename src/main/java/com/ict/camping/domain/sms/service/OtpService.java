package com.ict.camping.domain.sms.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class OtpService {

  private LocalDateTime lastRequestTime = LocalDateTime.MIN;  // 마지막 요청 시간 초기화
  private Map<String, String> otpStorage = new HashMap<>(); // 전화번호별 OTP 저장
  private static final int MIN_REQUEST_INTERVAL = 60;  // 최소 요청 간격 (초 단위)

  // otp 발송 가능한지 확인한다.(한 사람이 악의적으로 여러 번 보내기 방지)
  public boolean canRequestOtp(String phoneNumber) {
    LocalDateTime now = LocalDateTime.now();
    if (lastRequestTime.plusSeconds(MIN_REQUEST_INTERVAL).isAfter(now)) {
        return false;  // 최소 요청 간격을 넘기지 않으면 요청 불가
    }
    lastRequestTime = now;  // 마지막 요청 시간 업데이트
    return true;
  }

  // 인증번호 서버에 저장하는 메서드드
  public void storeOtp(String phoneNumber, String otp) {

    if (!canRequestOtp(phoneNumber)) {
      throw new IllegalStateException("You can request OTP only once every minute.");
    }
      otpStorage.put(phoneNumber, otp); //전화번호 별 OTP 저장
  }

  public boolean verifyOtp(String phone, String otp) {
      return otp.equals(otpStorage.get(phone));
  }
}