package com.ict.camping.domain.auth.vo;

import lombok.Data;

@Data
public class SnsAccountVO {
  private String user_idx, name, sns_user_id, sns_provider, sns_email;
}
