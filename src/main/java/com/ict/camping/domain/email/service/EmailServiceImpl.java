package com.ict.camping.domain.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ict.camping.domain.email.vo.VerificationCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmailServiceImpl implements EmailService {

    // 이메일 : 코드로 저장 (동기화된 맵 사용)
    private final Map<String, VerificationCode> verificationStore = new ConcurrentHashMap<>();

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean sendVerificationEmail(String toEmail) {
        // 6자리 랜덤 인증 코드 생성
        Random rand = new Random();
        int verificationCode = 100000 + rand.nextInt(900000);

        // 이메일 발송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("inkyum77@naver.com");
        message.setTo(toEmail);
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드: " + verificationCode);

        try {
            mailSender.send(message);
            VerificationCode verificationCodeObj = new VerificationCode(toEmail, String.valueOf(verificationCode), System.currentTimeMillis());
            verificationStore.put(toEmail, verificationCodeObj); // 이메일을 키로 사용하여 저장
            return true;
        } catch (Exception e) {
            // 예외 처리 (로깅 추가)
            System.err.println("이메일 발송 실패: " + e.getMessage());
            return false;
        }
    }

    // 인증번호 검증
    @Override
    public boolean verifyCode(String email, String inputCode) {
        VerificationCode storedCode = verificationStore.get(email);

        if (storedCode == null) {
            return false; // 인증번호 없음
        }

        // 인증번호 유효 시간 (3분) 체크
        long currentTime = System.currentTimeMillis();
        if ((currentTime - storedCode.getTimestamp()) > 180_000) { // 3분 = 180,000 밀리초
            verificationStore.remove(email); // 만료된 코드 제거
            return false;
        }

        // 코드 매칭 확인
        boolean isValid = storedCode.getCode().equals(inputCode);
        if (isValid) {
            verificationStore.remove(email); // 사용된 코드 제거
        }
        return isValid;
    }

    // 인증 코드 store에서 불러오는 메서드
    @Override
    public VerificationCode getVerificationCode(String email) {
        return verificationStore.get(email);
    }
}
