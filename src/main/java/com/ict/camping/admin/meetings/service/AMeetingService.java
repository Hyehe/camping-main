package com.ict.camping.admin.meetings.service;

import java.util.List;

import com.ict.camping.admin.meetings.vo.ALightningCommentVO;
import com.ict.camping.admin.meetings.vo.AMeetingVO;

public interface AMeetingService {
    // 리스트
    List<AMeetingVO> getAllMeetings();
    // 디테일
    AMeetingVO getMeetingDetail(String post_idx);
    // 수정
    int updateMeetings(AMeetingVO meetingVO);
    // 삭제
    int deleteMeeting(String post_idx);
    // 댓글
    List<ALightningCommentVO> getCommentsById(String post_idx);
    // 댓글 삭제
    boolean deleteComment(int comment_idx);
} 
