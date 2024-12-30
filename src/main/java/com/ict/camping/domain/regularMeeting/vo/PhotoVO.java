package com.ict.camping.domain.regularMeeting.vo;

import com.twilio.twiml.Text;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoVO {
  private int image_idx, image_url;
}
