package com.ict.camping.domain.regularMeeting.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegularMeetingVO {
    private int meeting_idx;
    private String name;
    private String description;
    private String profile_image;
    private String region;
    private String subregion;
    private int personnel;
    private int leader_idx;
    private String created_at;
    private boolean favorites_idx;
    private List<HashtagVO> hashtags;
    private List<Map<String, Object>> membersAvatar;

    private String leader_username;
    private String leader_avatar_url;

}
