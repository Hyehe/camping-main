package com.ict.camping.admin.meetings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.camping.admin.meetings.service.AMeetingService;
import com.ict.camping.admin.meetings.vo.ALightningCommentVO;
import com.ict.camping.admin.meetings.vo.AMeetingVO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/meeting")
public class AMeetingController {
    @Autowired
    private AMeetingService meetingService;

    @GetMapping("/meetings")
    public List<AMeetingVO> getAllMeetings() {
        List<AMeetingVO> allMeetings = meetingService.getAllMeetings();
        return allMeetings;
    }

    @GetMapping("comment/{post_idx}")
    public ResponseEntity<List<ALightningCommentVO>> getComments(@PathVariable("post_idx") String post_idx) {
        List<ALightningCommentVO> comments = meetingService.getCommentsById(post_idx);
        if (comments == null || comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(comments);
    }
    
    @DeleteMapping("/comment/delete/{comment_idx}")
    public ResponseEntity<String> deleteComment(@PathVariable("comment_idx") int comment_idx) {
        try {
            // 댓글 삭제 서비스 호출
            boolean isDeleted = meetingService.deleteComment(comment_idx);
            if (isDeleted) {
                return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제하려는 댓글이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 중 오류가 발생했습니다.");
        }
    }
    

    @GetMapping("/meetings/{post_idx}")
    public AMeetingVO getMeetingDetail(@PathVariable("post_idx") String post_idx) {
        return meetingService.getMeetingDetail(post_idx);
    }

    @PostMapping("/meetings/update/{post_idx}")
    public ResponseEntity<String> updateMeetings(@PathVariable("post_idx") String post_idx,
    @ModelAttribute AMeetingVO formData) {
        AMeetingVO existingMeeting = meetingService.getMeetingDetail(post_idx);
        if (existingMeeting == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 post_idx 해당되는 post가 존재하지 않음");
        }
        existingMeeting.setTitle(formData.getTitle() != null ? formData.getTitle() : existingMeeting.getTitle());
        existingMeeting.setContent(formData.getContent() != null ? formData.getContent() : existingMeeting.getContent());
        existingMeeting.setMeeting_date(formData.getMeeting_date() != null ? formData.getMeeting_date() : existingMeeting.getMeeting_date());
        existingMeeting.setMeeting_location(formData.getMeeting_location() != null ? formData.getMeeting_location() : existingMeeting.getMeeting_location());
        existingMeeting.setPersonnel(formData.getPersonnel() != 0 ? formData.getPersonnel() : existingMeeting.getPersonnel());
        int result = meetingService.updateMeetings(existingMeeting);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("정보 업데이트에 실패했습니다.");
        }
        return ResponseEntity.ok("정보가 성공적으로 업데이트되었습니다.");
    }
    
    @DeleteMapping("/meetings/delete/{post_idx}")
    public ResponseEntity<String> deleteMeeting(@PathVariable("post_idx") String post_idx) {
        AMeetingVO existingMeeting = meetingService.getMeetingDetail(post_idx);
        if (existingMeeting == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 post_idx에 해당되는 post가 존재하지 않음");
        }
        int result = meetingService.deleteMeeting(post_idx);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제에 실패했습니다.");
        }
        return ResponseEntity.ok("정보가 성공적으로 삭제되었습니다.");
    }
    
}
