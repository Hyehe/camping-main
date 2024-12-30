package com.ict.camping.domain.sms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.camping.domain.auth.vo.DataVO;
import com.ict.camping.domain.sms.service.OtpService;
import com.ict.camping.domain.sms.service.SmsService;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

  
  private final SmsService smsService;

  @Autowired
  private OtpService otpService;

  // 생성자 주입
  public SmsController(SmsService smsService) {
      this.smsService = smsService;
  }


  // {"", ""} 형식으로 받는다.
  @PostMapping("/send-otp")
  public DataVO sendOtp(@RequestBody Map<String, String> request) {
      DataVO dataVO = new DataVO();
      String phone = request.get("phone");
      String otp;
  
      try {
          otp = smsService.sendOtp(phone); // SMS 서비스 호출
      } catch (Exception e) {
          otp = null; // 예외 발생 시 OTP를 null로 설정
      }
  
      if (otp != null && !otp.isEmpty()) {
          dataVO.setSuccess(true);
          dataVO.setMessage(phone + " 번호로 인증번호가 발송되었습니다.");
          otpService.storeOtp(phone, otp); // OTP 저장 (DB 또는 캐시)
      } else {
          dataVO.setSuccess(false);
          dataVO.setMessage("인증번호 발송에 실패했습니다.");
      }
  
      return dataVO; // 결과 반환
  }
  

  @PostMapping("/verify-otp")
  public DataVO verifyOtp(@RequestBody Map<String, String> request) {
    DataVO dataVO = new DataVO();
    String phone = request.get("phone");
    String otp = request.get("otp");

    if (otpService.verifyOtp(phone, otp)) {
      dataVO.setSuccess(true);
      dataVO.setMessage("인증 성공");
    } else {
      dataVO.setSuccess(false);
      dataVO.setMessage("인증 실패");
    }

    return dataVO;
  }
}
