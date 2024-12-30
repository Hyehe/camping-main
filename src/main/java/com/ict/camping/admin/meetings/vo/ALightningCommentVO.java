package com.ict.camping.admin.meetings.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ALightningCommentVO {
    private int comment_idx;
    private int post_idx;
    private int writer_idx;
    private String content;
    private String created_at;
}
