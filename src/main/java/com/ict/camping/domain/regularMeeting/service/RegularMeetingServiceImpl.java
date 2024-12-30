package com.ict.camping.domain.regularMeeting.service;

import com.ict.camping.domain.regularMeeting.mapper.RegularMeetingMapper;
import com.ict.camping.domain.regularMeeting.vo.HashtagVO;
import com.ict.camping.domain.regularMeeting.vo.RegularMeetingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RegularMeetingServiceImpl implements RegularMeetingService {

    @Autowired
    private RegularMeetingMapper regularMeetingMapper;

    @Override
    @Transactional
    public int createMeeting(RegularMeetingVO meeting) {
        regularMeetingMapper.insertMeeting(meeting);
        // MyBatis가 useGeneratedKeys="true"로 설정되어 있으므로, meeting_idx가 설정됨
        if (meeting.getHashtags() != null) {
            for (HashtagVO hashtag : meeting.getHashtags()) {
                int hashtagIdx = findHashtagByName(hashtag.getName());
                if (hashtagIdx > 0) {
                    regularMeetingMapper.insertMeetingHashtags(meeting.getMeeting_idx(), hashtagIdx);
                } 
            }
        }
        return meeting.getMeeting_idx();
    }

    @Override
    public List<RegularMeetingVO> getAllMeetings(int userIdx) {
        return regularMeetingMapper.selectAllMeetings(userIdx);
    }

    @Override
    public RegularMeetingVO getMeetingById(int meetingIdx, int userIdx) {
        return regularMeetingMapper.selectMeetingById(meetingIdx, userIdx);
    }

    @Override
    @Transactional
    public void updateMeeting(RegularMeetingVO meeting) {
        regularMeetingMapper.updateMeeting(meeting);

        // 기존 해시태그 연결 삭제 (필요 시 매퍼에 deleteMeetingHashtags 메서드 추가)
        // regularMeetingMapper.deleteMeetingHashtags(meeting.getMeeting_idx());

        // 새로운 해시태그 연결
        if (meeting.getHashtags() != null) {
            for (HashtagVO hashtag : meeting.getHashtags()) {
                regularMeetingMapper.insertMeetingHashtags(meeting.getMeeting_idx(), hashtag.getHashtag_idx());
            }
        }
    }

    @Override
    @Transactional
    public void deleteMeeting(int meetingIdx) {
        regularMeetingMapper.deleteMeeting(meetingIdx);
        // 관련 해시태그 연결 삭제 (필요 시 매퍼에 deleteMeetingHashtags 메서드 추가)
        // regularMeetingMapper.deleteMeetingHashtags(meetingIdx);
    }

    @Override
    public boolean isFavorite(int userIdx, int meetingIdx) {
        return regularMeetingMapper.checkFavorite(userIdx, meetingIdx);
    }

    @Override
    @Transactional
    public boolean toggleFavorite(int userIdx, int meetingIdx) {
        boolean isFavorite = regularMeetingMapper.checkFavorite(userIdx, meetingIdx);
        if (isFavorite) {
            regularMeetingMapper.deleteFavorite(userIdx, meetingIdx);
            return false;
        } else {
            regularMeetingMapper.insertFavorite(userIdx, meetingIdx);
            return true;
        }
    }

    @Override
    public boolean doesHashtagExist(String hashtag) {
        return regularMeetingMapper.findHashtagByName(hashtag) > 0;
    }

    @Override
    public int findHashtagByName(String hashtag) {
        return regularMeetingMapper.findHashtagByName(hashtag);
    }

    @Override
    public List<HashtagVO> getAllHashtags() {
        return regularMeetingMapper.selectAllHashtags();
    }

    @Override
    public void insertMeetingHashtags(int meetingIdx, int hashtagIdx) {
        regularMeetingMapper.insertMeetingHashtags(meetingIdx, hashtagIdx);
    }

    @Override
    public List<Map<String, Object>> getMeetingMembersProfile(int meetingIdx) {
        return regularMeetingMapper.selectMeetingMembersProfile(meetingIdx);
    }



    
}
