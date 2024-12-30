package com.ict.camping.domain.email.vo;

import lombok.Data;

@Data
public class EmailVO {
  private String email, verificationCode;
}
