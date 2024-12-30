package com.ict.camping.domain.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ict.camping.domain.auth.vo.UserDetailsVO;

@Mapper
public interface AuthMapper {
  UserDetailsVO getUserDetail (@Param("id") String id);
}
