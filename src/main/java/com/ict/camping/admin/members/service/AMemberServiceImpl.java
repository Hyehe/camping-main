package com.ict.camping.admin.members.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.camping.admin.members.mapper.AMemberMapper;
import com.ict.camping.admin.members.vo.AMemberVO;
import com.ict.camping.admin.members.vo.AOperatorVO;

@Service
public class AMemberServiceImpl implements AMemberService{
    @Autowired
    private AMemberMapper memberMapper;

    @Override
    public List<AMemberVO> getAllMembers() {
        return memberMapper.findAllMembers();
    }
    
    @Override
    public AMemberVO getMemberDetail(String user_idx) {
        return memberMapper.findMemberDetail(user_idx);
    }

    @Override
    public List<AOperatorVO> getAllOperators() {
        return memberMapper.findAllOperators();
    }

    @Override
    public int insertMembers(AMemberVO memberVO) {
       return memberMapper.insertMembers(memberVO);
    }

    @Override
    public int insertOperators(AOperatorVO operatorVO) {
        return memberMapper.insertOperators(operatorVO);
    }

    @Override
    public AOperatorVO getOperatorDetail(String business_idx) {
        return memberMapper.findOperatorDetail(business_idx);
    }


    // id 중복검사
    @Override
    public boolean isIdDuplicate(String id) {
        return memberMapper.checkIdDuplicate(id) > 0 ;
    }

    @Override
    public boolean isIdxDuplicate(String user_idx) {
        return memberMapper.checkIdxDuplicate(user_idx) > 0;
    }

    @Override
    public int updateMembers(AMemberVO memberVO) {
        return memberMapper.updateMembers(memberVO);    
    }

    @Override
    public boolean isBuisnessDuplicate(String user_idx) {
        return memberMapper.checkUserDuplicate(user_idx) > 0;    
    }

    @Override
    public int updateBusiness(AOperatorVO operatorVO) {
        return memberMapper.updateBusiness(operatorVO);
    }

    
}
