package com.ict.camping.admin.meetings.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AMeetingVO {
    private int post_idx;
    private String title;
    private String content;
    private int writer_idx;
    private String meeting_date;
    private String meeting_location;
    private String created_at;
    private int personnel;

    private String username;
    private String id;
    private String email;
}
