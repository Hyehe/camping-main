package com.ict.camping.admin.inquiry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.camping.admin.inquiry.service.AInquiryService;
import com.ict.camping.admin.inquiry.vo.AInquiryVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/inquiry")
public class AInquiryController {

    @Autowired
    private AInquiryService inquiryService;

    @GetMapping("/inquiries")
    public List<AInquiryVO> getAllInquiries() {
        List<AInquiryVO> allInquiries = inquiryService.getAllInquiry();
        return allInquiries;
    }

    @GetMapping("/inquiries/{inquiry_idx}")
    public AInquiryVO getInquiryDetail(@PathVariable("inquiry_idx") String inquiry_idx) {
        return inquiryService.getInquiryDetail(inquiry_idx);
    }

    
    // @DeleteMapping("/admins/delete/{admin_idx}")
    // public ResponseEntity<String> deleteAdmin(@PathVariable("admin_idx") String admin_idx) {
    //     try {
    //         // 댓글 삭제 서비스 호출
    //         boolean isDeleted = adminService.deleteAdmin(admin_idx);
    //         if (isDeleted) {
    //             return ResponseEntity.ok("관리자가 성공적으로 삭제되었습니다.");
    //         } else {
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제하려는 관리자가 존재하지 않습니다.");
    //         }
    //     } catch (Exception e) {
    //         // 예외 처리
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("관리자 삭제 중 오류가 발생했습니다.");
    //     }
    // }

    // @PostMapping("/admins/insert")
    // public int insertAdmin(@ModelAttribute("data") AdminVO formData) {
    //     int result = adminService.insertAdmin(formData);
    //     if (result == 0) {
    //         System.out.println("데이터 삽입 실패");
    //         return 0;
    //     }
    //     System.out.println("데이터 삽입 성공");
    //     return result;
    // }

    // @GetMapping("/admins/check-id")
    // public boolean checkIdDuplicate(@RequestParam("user_idx") String user_idx) {
    //     boolean isDuplicate = adminService.isIdDuplicate(user_idx);
    //     if (isDuplicate) {
    //         System.out.println("이미 존재");
    //     } else {
    //         System.out.println("사용가능");
    //     }
    //     return isDuplicate;
    // }
    
    


    
}
