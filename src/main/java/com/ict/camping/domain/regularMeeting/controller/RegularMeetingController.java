package com.ict.camping.domain.regularMeeting.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ict.camping.domain.regularMeeting.service.RegularMeetingService;
import com.ict.camping.domain.regularMeeting.vo.HashtagVO;
import com.ict.camping.domain.regularMeeting.vo.RegularMeetingVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/regular-meetings")
public class RegularMeetingController {

    @Autowired
    private RegularMeetingService regularMeetingService;

    public RegularMeetingController(RegularMeetingService regularMeetingService) {
        this.regularMeetingService = regularMeetingService;
    }

    // 1) 이미지 파일 업로드 + 모임생성
    @PostMapping(consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createMeeting(
            @RequestParam("user_idx") int userIdx,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("region") String region,
            @RequestParam("subregion") String subregion,
            @RequestParam("personnel") int personnel,
            @RequestParam("hashtags") String hashtags, // CSV
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // 1) 파일 업로드
            String storedFileName = "";
            if (file != null && !file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String ext = "";
                if (originalFilename.lastIndexOf(".") != -1) {
                    ext = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                storedFileName = System.currentTimeMillis() + ext; // 예: 1694492012345.gif

                Path uploadDir = Paths.get("uploads/");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                Path filePath = uploadDir.resolve(storedFileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // 2) 모임 데이터 저장
            RegularMeetingVO meeting = new RegularMeetingVO();
            meeting.setName(name);
            meeting.setDescription(description);
            meeting.setRegion(region);
            meeting.setSubregion(subregion);
            meeting.setPersonnel(personnel);
            meeting.setProfile_image(storedFileName);
            meeting.setLeader_idx(userIdx);

            // Create meeting and get meetingIdx
            int meetingIdx = regularMeetingService.createMeeting(meeting);

            // 3) 해시태그 연결
            // 해시태그를 분리
            for (String hashtag : hashtags.split(",")) {
                String trimTag = hashtag.trim();
                int hashtagIdx = regularMeetingService.findHashtagByName(trimTag);
                if (hashtagIdx > 0) { // 존재할 때만 연결
                    regularMeetingService.insertMeetingHashtags(meetingIdx, hashtagIdx);
                }
                // 해시태그가 존재하지 않을 경우, 해시태그 생성 후 연결 로직 추가 가능
            }

            return ResponseEntity.ok("Regular meeting created successfully!");
        } catch (java.io.IOException e) {
            log.error("File upload failed", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file upload");
        } catch (Exception e) {
            log.error("Error creating meeting", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating meeting");
        }
    }

    // 2) 전체 목록 조회
    @GetMapping
    public ResponseEntity<?> getAllMeetings(@RequestParam(value = "user_idx", required = false, defaultValue = "0") int userIdx) {
        try {
            // user_idx가 0이면 비로그인 유저로 처리
            List<RegularMeetingVO> meetings = regularMeetingService.getAllMeetings(userIdx);
            log.info("Meetings: {}", meetings);

            List<HashtagVO> hashtags = regularMeetingService.getAllHashtags();

            Map<String, Object> response = new HashMap<>();
            response.put("meetings", meetings);
            response.put("hashtags", hashtags);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching meetings", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching meetings");
        }
    }

    // 3) 특정 모임 상세조회
    @GetMapping("/{meetingId}")
    public ResponseEntity<?> getMeetingById(
            @PathVariable("meetingId") int meetingId,
            @RequestParam(value = "user_idx", required = false, defaultValue = "0") int userIdx) {
        try {
            RegularMeetingVO meeting = regularMeetingService.getMeetingById(meetingId, userIdx);
            if (meeting != null) {
                return ResponseEntity.ok(meeting);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meeting not found");
            }
        } catch (Exception e) {
            log.error("Error fetching meeting by ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching meeting");
        }
    }

    // 특정 모임마다 가입 멤버
    @GetMapping("/{meetingId}/members")
    public ResponseEntity<?> getMeetingMembersProfile(@PathVariable int meetingId) {
        try {
            List<Map<String, Object>> members = regularMeetingService.getMeetingMembersProfile(meetingId);
            return ResponseEntity.ok(members);
        } catch (Exception e) {
            log.error("Error fetching meeting members", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching members");
        }
    }

    

    // 4) 좋아요
    // @PostMapping("/{meetingId}/favorite")
    // public ResponseEntity<?> toggleFavorite(
    //         @PathVariable int meetingId,
    //         @RequestParam("user_idx") int userIdx) {
    //     try {
    //         boolean isFavorite = regularMeetingService.toggleFavorite(userIdx, meetingId);
    //         return ResponseEntity.ok(Map.of("success", true, "favorite", isFavorite));
    //     } catch (Exception e) {
    //         log.error("Error toggling favorite", e);
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "error", e.getMessage()));
    //     }
    // }

    // 좋아요 토글 메서드
    @PostMapping("/{meetingId}/favorite")
     @PreAuthorize("isAuthenticated()") // 인증된 사용자만 접근 가능
    public ResponseEntity<?> toggleFavorite(
            @PathVariable int meetingId,
            @RequestParam("user_idx") int userIdx) { 
        try {
            boolean isFavorite = regularMeetingService.toggleFavorite(userIdx, meetingId);
            return ResponseEntity.ok(Map.of("success", true, "favorite", isFavorite));
        } catch (Exception e) {
            log.error("Error toggling favorite", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    

    // 5) 해시태그 목록 조회
    @GetMapping("/hashtags")
    public ResponseEntity<?> getAllHashtags() {
        try {
            List<HashtagVO> hashtags = regularMeetingService.getAllHashtags();
            return ResponseEntity.ok(hashtags);
        } catch (Exception e) {
            log.error("Error fetching hashtags", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching hashtags");
        }
    }
}
