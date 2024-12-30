package com.ict.camping.admin.members.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AMemberVO {
    private int user_idx;
    private String id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String sns_email_kakao;
    private String sns_email_google;
    private String sns_provider;
    private String sns_email_naver;
    private String address;
    private String join_date;
    private String warn;
    private String type;
}
