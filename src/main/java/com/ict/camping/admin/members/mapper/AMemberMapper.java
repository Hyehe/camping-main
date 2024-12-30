package com.ict.camping.admin.members.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.camping.admin.members.vo.AMemberVO;
import com.ict.camping.admin.members.vo.AOperatorVO;

@Mapper
public interface AMemberMapper {
    List<AMemberVO> findAllMembers();
    AMemberVO findMemberDetail(String user_idx);
    int checkIdDuplicate(String id);

    int checkIdxDuplicate(String id);

    int updateMembers(AMemberVO memberVO);

    int updateBusiness(AOperatorVO operatorVO);

    List<AOperatorVO> findAllOperators();

    AOperatorVO findOperatorDetail(String user_idx);

    int insertMembers(AMemberVO memberVO);

    int insertOperators(AOperatorVO operatorVO);

    int checkUserDuplicate(String user_idx);
}
