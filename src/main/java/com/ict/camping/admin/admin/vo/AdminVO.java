package com.ict.camping.admin.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminVO {
    private int admin_idx;
    private int user_idx;
    private String admin_type;
    private String assigned_at;

    // join
    private String username;
    private String email;
    private String phone;
    private String id;
}
