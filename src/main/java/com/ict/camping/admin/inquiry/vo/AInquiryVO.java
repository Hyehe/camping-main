package com.ict.camping.admin.inquiry.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AInquiryVO {
    private int inquiry_idx;
    private int user_idx;
    private String subject;
    private String content;
    private String created_at;
    private String f_name;

    // join
    private String username;
    private String email;
    private String phone;
    private String id;
}
