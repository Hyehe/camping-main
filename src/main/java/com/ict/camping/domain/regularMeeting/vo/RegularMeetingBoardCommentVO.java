package com.ict.camping.domain.regularMeeting.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegularMeetingBoardCommentVO {
  private int comment_idx, user_idx, board_idx, parent_id;
  private String comment_content, uploaded_at;
}
