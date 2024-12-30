package com.ict.camping.admin.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.camping.admin.admin.mapper.AdminMapper;
import com.ict.camping.admin.admin.vo.AdminVO;


@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<AdminVO> getAllAdmins() {
        return adminMapper.findAllAdmins();
    }

    @Override
    public AdminVO getAdminDetail(String admin_idx) {
        return adminMapper.findAdminDetail(admin_idx);
    }

    @Override
    public boolean deleteAdmin(String admin_idx) {
        int rowsAffected = adminMapper.deleteAdmin(admin_idx);
        return rowsAffected > 0;
    }

    @Override
    public int insertAdmin(AdminVO adminVO) {
        return adminMapper.insertAdmin(adminVO);
    }

    @Override
    public boolean isIdDuplicate(String user_idx) {
        return adminMapper.checkIdDuplicate(user_idx) > 0;
    }

    
}
