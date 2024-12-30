package com.ict.camping.domain.users.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ict.camping.domain.auth.vo.UserDetailsVO;
import com.ict.camping.domain.users.vo.UsersVO;

@Mapper
public interface UsersMapper  {
  int joinUser(UsersVO mvo);
  int joinBusinessUser(UsersVO mvo);
  String getUserIdxById(String m_id);
  String usersIdCheck(String m_id);
  UsersVO getUsersById(String m_id);
  UsersVO findUserByProvider(UsersVO mvo);
  int insertUser(UsersVO mvo);
  UserDetailsVO getUserById(String m_id);
  
}
