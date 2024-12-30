package com.ict.camping.domain.email.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.camping.domain.auth.vo.DataVO;
import com.ict.camping.domain.email.service.EmailService;
import com.ict.camping.domain.email.vo.EmailVO;
import com.ict.camping.domain.email.vo.VerificationCode;

@RestController
@RequestMapping("/api/signup")
public class EmailController {

  @Autowired
  private EmailService emailService;

  private LocalDateTime emailSentTime; // 이메일 발송 시간

    @PostMapping("/sendVerificationEmail")
    public DataVO sendVerificationEmail(@RequestBody EmailVO evo) {

      String email = evo.getEmail();

      System.out.println(email);
      DataVO dataVO = new DataVO();
      boolean emailSended = emailService.sendVerificationEmail(email);
      if (emailSended) {
        dataVO.setSuccess(true);
        dataVO.setMessage("발송 완료");
      } else {
        dataVO.setSuccess(false);
        dataVO.setMessage("발송 실패");
      }

      return dataVO;
    }

    @PostMapping("/verifyEmail")
    public DataVO verifyEmail(@RequestBody EmailVO evo) {
      DataVO dataVO = new DataVO();
      String email = evo.getEmail();
      String verificationCode = evo.getVerificationCode();

      // store에 저장된 인증코드(email: (인증코드, 이메일, 인증시간))
      VerificationCode storedCode = emailService.getVerificationCode(email);
      if (storedCode == null) {
        dataVO.setSuccess(false);
        dataVO.setMessage("이메일 인증 요청이 없습니다.");
        return dataVO;
      }

      // if (emailSentTime != null) {
      //   long minutesElapsed = ChronoUnit.MINUTES.between(emailSentTime, LocalDateTime.now());
      //   if (minutesElapsed > 3) {
      //     dataVO.setSuccess(false);
      //     dataVO.setMessage("인증시간이 만료되었습니다.");
      //     return dataVO;
      //   }
      // }

      // 인증 시간 만료 여부 확인
      long minutesElapsed = ChronoUnit.MINUTES.between(
        LocalDateTime.ofInstant(Instant.ofEpochMilli(storedCode.getTimestamp()), ZoneId.systemDefault()), 
        LocalDateTime.now()
      );

      if (minutesElapsed > 3) {
          dataVO.setSuccess(false);
          dataVO.setMessage("인증시간이 만료되었습니다.");
          return dataVO;
      }
      
      // 인증 코드 검증 로직
      boolean isVerified = emailService.verifyCode(email, verificationCode);
      
      if (isVerified) {
        dataVO.setSuccess(true);
        dataVO.setMessage("인증 성공");
      } else {
        dataVO.setSuccess(false);
        dataVO.setMessage("인증 번호가 틀립니다.");
      }
      return dataVO;
    }
}
