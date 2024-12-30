package com.ict.camping.domain.sms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ict.camping.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {
  
  private final TwilioConfig twilioConfig;

  // 생성자 주입
  public SmsService(TwilioConfig twilioConfig) {
      this.twilioConfig = twilioConfig;
      Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
  }

  // otp 요청 보내기
  public String sendOtp(String toPhoneNumber) {
    int otp = (int) (Math.random() * 9000) + 1000; // 4자리 OTP 생성
    String messageBody = String.valueOf(otp);

        // E.164 형식으로 전화번호를 변환 (예: +82 103076XXXX)
    String formattedPhoneNumber = formatPhoneNumber(toPhoneNumber);

    // creator : 객체 생성
    // create : 요청 보내기
    // 메시지 전송 요청
    Message message = Message.creator(
            new PhoneNumber(formattedPhoneNumber), // 수신자 번호
            new PhoneNumber(twilioConfig.getPhoneNumber()), // 발신자 번호
            messageBody // 메시지 내용
    ).create();

    // 메시지 상태 확인
    if ("sent".equalsIgnoreCase(message.getStatus().toString()) || 
        "queued".equalsIgnoreCase(message.getStatus().toString())) {
        return messageBody; // 성공적으로 전송됨
    } else {
      throw new IllegalStateException("Failed to send OTP. Status: " + message.getStatus());
    }
  }

  // 전화번호를 E.164 형식으로 변환하는 메서드 예시
  private String formatPhoneNumber(String phoneNumber) {
    // 전화번호가 이미 E.164 형식일 경우 그대로 반환
    if (phoneNumber.startsWith("+")) {
        return phoneNumber;
    }

    // 예시: 한국 전화번호의 경우 국가 코드 +82를 추가
    return "+82" + phoneNumber.substring(1);  // '010'의 첫 번째 0을 제거하고 국가 코드 추가
  }
}
