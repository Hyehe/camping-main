package com.ict.camping.admin.admin.service;

import java.util.List;

import com.ict.camping.admin.admin.vo.AdminVO;

public interface AdminService {
    // 리스트
    List<AdminVO> getAllAdmins();
    // 디테일
    AdminVO getAdminDetail(String admin_idx);
    // 삭제
    boolean deleteAdmin(String admin_idx);
    // 삽입
    int insertAdmin(AdminVO adminVO);
    // 중복확인
    boolean isIdDuplicate(String user_idx);
} 
