package com.ict.camping.admin.inquiry.service;

import java.util.List;

import com.ict.camping.admin.admin.vo.AdminVO;
import com.ict.camping.admin.inquiry.vo.AInquiryVO;

public interface AInquiryService {
    // 리스트
    List<AInquiryVO> getAllInquiry();
    // 디테일
    AInquiryVO getInquiryDetail(String inquiry_idx);
    // 삭제
    boolean deleteAdmin(String admin_idx);
    // 삽입
    int insertAdmin(AdminVO adminVO);
    // 중복확인
    boolean isIdDuplicate(String user_idx);
} 
