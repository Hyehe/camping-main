package com.ict.camping.domain.regularMeeting.mapper;

import com.ict.camping.domain.regularMeeting.vo.HashtagVO;
import com.ict.camping.domain.regularMeeting.vo.RegularMeetingVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface RegularMeetingMapper {

    // 1) 정규 모임 생성
    void insertMeeting(RegularMeetingVO meeting);

    // 2) 전체 모임 목록 가져오기
    List<RegularMeetingVO> selectAllMeetings(@Param("userIdx") int userIdx);

    // 3) 특정 모임 조회 (단건)
    RegularMeetingVO selectMeetingById(@Param("meetingId") int meetingId, @Param("userIdx") int userIdx);

    // 4) 해시태그 존재 여부 조회
    int findHashtagByName(@Param("hashtag") String hashtag);

    // 5) 해시태그 목록 전부 가져오기
    List<HashtagVO> selectAllHashtags();

    // 6) 모임-해시태그 연결 삽입
    void insertMeetingHashtags(@Param("meetingIdx") int meetingIdx, @Param("hashtagIdx") int hashtagIdx);

    // 좋아요 상태 확인
    boolean checkFavorite(@Param("userIdx") int userIdx, @Param("meetingIdx") int meetingIdx);

    // 좋아요 추가
    void insertFavorite(@Param("userIdx") int userIdx, @Param("meetingIdx") int meetingIdx);

    // 좋아요 삭제
    void deleteFavorite(@Param("userIdx") int userIdx, @Param("meetingIdx") int meetingIdx);

    // 모임 가입 멤버 3명 프사
    List<Map<String, Object>> selectMeetingMembersProfile(@Param("meetingIdx") int meetingIdx);

    // 특정 모임의 해시태그 조회
    List<HashtagVO> selectMeetingHashtags(@Param("meetingIdx") int meetingIdx);

    // 7) 정규 모임 업데이트
    void updateMeeting(RegularMeetingVO meeting);

    // 8) 정규 모임 삭제
    void deleteMeeting(@Param("meetingIdx") int meetingIdx);
}
