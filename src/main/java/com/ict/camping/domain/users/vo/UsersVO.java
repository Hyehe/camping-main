package com.ict.camping.domain.users.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersVO {
  private String user_idx, id, password, username, avatar_url, phone, email, address, address_detail, zipcode, warn, sns_email_naver, sns_email_kakao, sns_email_google, sns_provider, type;
  private String business_number, business_name, business_ceo, started_date, contentId;
}
