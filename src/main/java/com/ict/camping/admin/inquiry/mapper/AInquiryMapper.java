package com.ict.camping.admin.inquiry.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ict.camping.admin.admin.vo.AdminVO;
import com.ict.camping.admin.inquiry.vo.AInquiryVO;
@Mapper
public interface AInquiryMapper {

    List<AInquiryVO> findAllInquiry();

    AInquiryVO findInquiryDetail(String inquiry_idx);

    int deleteAdmin(String admin_idx);
    int insertAdmin(AdminVO adminVO);

    int checkIdDuplicate(String user_idx);
}
