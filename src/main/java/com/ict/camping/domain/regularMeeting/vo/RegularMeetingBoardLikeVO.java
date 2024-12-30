package com.ict.camping.domain.regularMeeting.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegularMeetingBoardLikeVO {
  private int likes_idx, user_idx, board_idx;
}
