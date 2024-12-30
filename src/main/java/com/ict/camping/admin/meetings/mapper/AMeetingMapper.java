package com.ict.camping.admin.meetings.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.camping.admin.meetings.vo.ALightningCommentVO;
import com.ict.camping.admin.meetings.vo.AMeetingVO;

@Mapper
public interface AMeetingMapper {
    List<AMeetingVO> findAllMeetings();

    AMeetingVO findMeetingDetail(String post_idx);

    int updateMeetings(AMeetingVO meetingVO);

    int deleteMeeting(String post_idx);

    List<ALightningCommentVO> findCommentsByPostIdx(String post_idx);

    int deleteCommentById(int comment_idx); // 삭제된 행 수 반환
}
