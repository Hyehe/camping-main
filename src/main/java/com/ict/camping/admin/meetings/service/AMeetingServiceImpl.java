package com.ict.camping.admin.meetings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.camping.admin.meetings.mapper.AMeetingMapper;
import com.ict.camping.admin.meetings.vo.ALightningCommentVO;
import com.ict.camping.admin.meetings.vo.AMeetingVO;


@Service
public class AMeetingServiceImpl implements AMeetingService{
    @Autowired
    private AMeetingMapper meetingMapper;

    @Override
    public List<AMeetingVO> getAllMeetings() {
        return meetingMapper.findAllMeetings();
    }

    @Override
    public AMeetingVO getMeetingDetail(String post_idx) {
        return meetingMapper.findMeetingDetail(post_idx);    
    }

    @Override
    public int updateMeetings(AMeetingVO meetingVO) {
        return meetingMapper.updateMeetings(meetingVO);
    }

    @Override
    public int deleteMeeting(String post_idx) {
        return meetingMapper.deleteMeeting(post_idx);
    }

    @Override
    public List<ALightningCommentVO> getCommentsById(String post_idx) {
        return meetingMapper.findCommentsByPostIdx(post_idx);
    }

    @Override
    public boolean deleteComment(int comment_idx) {
        int rowsAffected = meetingMapper.deleteCommentById(comment_idx);
        return rowsAffected > 0;
    }
    

    


    
}
