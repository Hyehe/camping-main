package com.ict.camping.admin.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.camping.admin.admin.vo.AdminVO;

@Mapper
public interface AdminMapper {
    List<AdminVO> findAllAdmins();
    AdminVO findAdminDetail(String admin_idx);
    int deleteAdmin(String admin_idx);
    int insertAdmin(AdminVO adminVO);
    int checkIdDuplicate(String user_idx);
}
