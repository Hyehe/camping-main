package com.ict.camping.domain.email.service;

import com.ict.camping.domain.email.vo.VerificationCode;

public interface EmailService {
  public boolean sendVerificationEmail(String toEmail);
  public boolean verifyCode(String email, String inputCode);
  public VerificationCode getVerificationCode(String email);
}
