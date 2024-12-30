package com.ict.camping.domain.regularMeeting.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegularMeetingBoardVO {
  private int board_idx,meeting_idx,user_idx,image_idx;
  private String board_content, uploaded_at;
}
