package com.ict.camping.domain.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.camping.common.util.JwtUtil;
import com.ict.camping.domain.auth.vo.DataVO;
import com.ict.camping.domain.auth.vo.UserDetailsVO;
import com.ict.camping.domain.users.vo.UsersVO;

@Service
public class AuthService {

  @Autowired
  private MyUserDetailService myUserDetailService;

  @Autowired
  private JwtUtil jwtUtil;

  public DataVO authenticate(UsersVO uvo){ 
    DataVO dataVO = new DataVO();
    try {
      //실제 DB 갔다오기
      UserDetailsVO udvo = myUserDetailService.getUserDetail(uvo.getId());
      String jwt = jwtUtil.generateToken(udvo.getId());

      dataVO.setSuccess(true);
      dataVO.setToken(jwt);
      dataVO.setUserDetails(udvo);
      return dataVO;

    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage(e.getMessage());
      return dataVO;
    }
  }
}
