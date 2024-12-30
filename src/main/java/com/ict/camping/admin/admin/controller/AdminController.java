package com.ict.camping.admin.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.camping.admin.admin.service.AdminService;
import com.ict.camping.admin.admin.vo.AdminVO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/admins")
    public List<AdminVO> getAllMeetings() {
        List<AdminVO> allAdmins = adminService.getAllAdmins();
        return allAdmins;
    }

    @GetMapping("/admins/{admin_idx}")
    public AdminVO getAdminDetail(@PathVariable("admin_idx") String admin_idx) {
        return adminService.getAdminDetail(admin_idx);
    }

    
    @DeleteMapping("/admins/delete/{admin_idx}")
    public ResponseEntity<String> deleteAdmin(@PathVariable("admin_idx") String admin_idx) {
        try {
            // 댓글 삭제 서비스 호출
            boolean isDeleted = adminService.deleteAdmin(admin_idx);
            if (isDeleted) {
                return ResponseEntity.ok("관리자가 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제하려는 관리자가 존재하지 않습니다.");
            }
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("관리자 삭제 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/admins/insert")
    public int insertAdmin(@ModelAttribute("data") AdminVO formData) {
        int result = adminService.insertAdmin(formData);
        if (result == 0) {
            System.out.println("데이터 삽입 실패");
            return 0;
        }
        System.out.println("데이터 삽입 성공");
        return result;
    }

    @GetMapping("/admins/check-id")
    public boolean checkIdDuplicate(@RequestParam("user_idx") String user_idx) {
        boolean isDuplicate = adminService.isIdDuplicate(user_idx);
        if (isDuplicate) {
            System.out.println("이미 존재");
        } else {
            System.out.println("사용가능");
        }
        return isDuplicate;
    }
    
    


    
}
