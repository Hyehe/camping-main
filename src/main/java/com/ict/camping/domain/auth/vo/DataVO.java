package com.ict.camping.domain.auth.vo;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getset
@AllArgsConstructor // 매개변수 o
@NoArgsConstructor  // 매개변수 x
public class DataVO {
  private boolean success;
  private Object data;
  private String token;
  private String message;
  private UserDetails userDetails;
}

    // 사용 방법
    // DataVO dataVO = new DataVO()
    // dataVO.setSuccess(true);
    // dataVO.setData(list) 또는 dataVO.setData(MemberVO) 
    // dataVO.setToken(token)

    // 사용 방법2
    // Map<String,Object> resultMap = new HashMap<>();
    // resultMap.put("list", MemberList)
    // resultMap.put("rowTotal", rowTotal)