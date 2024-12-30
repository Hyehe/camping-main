package com.ict.camping.domain.map.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.ict.camping.domain.map.service.CampingService;
import com.ict.camping.domain.map.vo.MapVO;


import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/camping")
public class CampingController {

    @Autowired
    private CampingService campingService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 특정 지역의 캠핑장 목록 조회
    @GetMapping("/sites")
    public List<MapVO> getCampingSites() {
        List<MapVO> campingSites = campingService.getAllCampingSites();
        return campingSites;
    }

    @GetMapping("/sites/{contentId}")
    public MapVO getCampingSite(@PathVariable("contentId") String contentId) {
        System.out.println("컨텐트 아이디가 제대로 오는지 보기 위한 sysout : "+contentId);
        return campingService.findCampingSiteByContentId(contentId);
    }
    @GetMapping("/sites/check-contentId")
    public boolean checkContentIdDuplicate(@RequestParam("contentId") String contentId) {
        boolean isDuplicate = campingService.isContentIdDuplicate(contentId);
        if (isDuplicate) {
            System.out.println("존재");
        } else {
            System.out.println("존재하지 않음");
        }
        return isDuplicate;
    }

    
    
    @PostMapping("/sites/insert/data")
    public ResponseEntity<String> postCampingSite(@ModelAttribute("data") MapVO formData) {
    MultipartFile file = formData.getFile(); 
    try {
        // 필수 필드 검증
        if (formData.getFacltNm() == null || formData.getFacltNm().isEmpty()) {
            return ResponseEntity.badRequest().body("캠핑장 이름은 필수 입력 값입니다.");
        }

        // 파일 처리
        if (file != null && !file.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String f_name = uuid + "_" + file.getOriginalFilename();
            formData.setFirstImageUrl(f_name);

            String path = "D:\\upload";
            File uploadDir = new File(path);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            file.transferTo(new File(uploadDir, f_name));
        } else {
            formData.setFirstImageUrl(null);
        }

        // 데이터 삽입
        int result = campingService.insertCampingSite(formData);

        if (result == 0) {
            System.err.println("캠핑장 데이터 삽입 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("캠핑장 데이터 삽입 실패");
        }

        System.out.println("캠핑장 데이터 삽입 성공");
        return ResponseEntity.ok("캠핑장 데이터 삽입 성공");

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생: " + e.getMessage());
    }
    
    }
    
    @PostMapping("/sites/update/{contentId}")
    public ResponseEntity<String> updateCampingSite(
    @PathVariable("contentId") String contentId,
    @ModelAttribute MapVO formData,
    @RequestPart(value = "file", required = false) MultipartFile file
) {
    try {
        // 파일이 존재하는 경우에만 처리
        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            String filePath = "D:/upload/" + originalFileName;
            file.transferTo(new File(filePath));
            formData.setFirstImageUrl(originalFileName);
        }

        // 기존 데이터 확인
        MapVO existingSite = campingService.findCampingSiteByContentId(contentId);
        if (existingSite == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 contentId에 대한 캠핑장 데이터를 찾을 수 없습니다.");
        }
        System.out.println("데이터 잘 오는지 확인용 아아아ㅏ아ㅏ아ㅏ아아아" + formData.getBizrno());
        // 데이터 업데이트: null 또는 기본값이면 기존 데이터 유지
        existingSite.setFacltNm(formData.getFacltNm() != null ? formData.getFacltNm() : existingSite.getFacltNm());
        existingSite.setIntro(formData.getIntro() != null ? formData.getIntro() : existingSite.getIntro());
        existingSite.setLineIntro(formData.getLineIntro() != null ? formData.getLineIntro() : existingSite.getLineIntro());
        existingSite.setAllar(formData.getAllar() != null ? formData.getAllar() : existingSite.getAllar());
        existingSite.setBizrno(formData.getBizrno() != null ? formData.getBizrno() : existingSite.getBizrno());
        existingSite.setFeatureNm(formData.getFeatureNm() != null ? formData.getFeatureNm() : existingSite.getFeatureNm());
        existingSite.setInduty(formData.getInduty() != null ? formData.getInduty() : existingSite.getInduty());
        existingSite.setLctCl(formData.getLctCl() != null ? formData.getLctCl() : existingSite.getLctCl());
        existingSite.setDoNm(formData.getDoNm() != null ? formData.getDoNm() : existingSite.getDoNm());
        existingSite.setSigunguNm(formData.getSigunguNm() != null ? formData.getSigunguNm() : existingSite.getSigunguNm());
        existingSite.setZipcode(formData.getZipcode() != null ? formData.getZipcode() : existingSite.getZipcode());
        existingSite.setAddr1(formData.getAddr1() != null ? formData.getAddr1() : existingSite.getAddr1());
        existingSite.setDirection(formData.getDirection() != null ? formData.getDirection() : existingSite.getDirection());
        existingSite.setTel(formData.getTel() != null ? formData.getTel() : existingSite.getTel());
        existingSite.setHomepage(formData.getHomepage() != null ? formData.getHomepage() : existingSite.getHomepage());
        existingSite.setSitedStnc(formData.getSitedStnc() != null ? formData.getSitedStnc() : existingSite.getSitedStnc());
        existingSite.setSiteMg1Width(formData.getSiteMg1Width() != null ? formData.getSiteMg1Width() : existingSite.getSiteMg1Width());
        existingSite.setSiteMg2Width(formData.getSiteMg2Width() != null ? formData.getSiteMg2Width() : existingSite.getSiteMg2Width());
        existingSite.setSiteMg3Width(formData.getSiteMg3Width() != null ? formData.getSiteMg3Width() : existingSite.getSiteMg3Width());
        existingSite.setSiteMg1Virticl(formData.getSiteMg1Virticl() != null ? formData.getSiteMg1Virticl() : existingSite.getSiteMg1Virticl());
        existingSite.setSiteMg2Virticl(formData.getSiteMg2Virticl() != null ? formData.getSiteMg2Virticl() : existingSite.getSiteMg2Virticl());
        existingSite.setSiteMg3Virticl(formData.getSiteMg3Virticl() != null ? formData.getSiteMg3Virticl() : existingSite.getSiteMg3Virticl());
        existingSite.setSiteBottomCl1(formData.getSiteBottomCl1() != null ? formData.getSiteBottomCl1() : existingSite.getSiteBottomCl1());
        existingSite.setSiteBottomCl2(formData.getSiteBottomCl2() != null ? formData.getSiteBottomCl2() : existingSite.getSiteBottomCl2());
        existingSite.setSiteBottomCl3(formData.getSiteBottomCl3() != null ? formData.getSiteBottomCl3() : existingSite.getSiteBottomCl3());
        existingSite.setSiteBottomCl4(formData.getSiteBottomCl4() != null ? formData.getSiteBottomCl4() : existingSite.getSiteBottomCl4());
        existingSite.setSiteBottomCl5(formData.getSiteBottomCl5() != null ? formData.getSiteBottomCl5() : existingSite.getSiteBottomCl5());
        existingSite.setSbrsCl(formData.getSbrsCl() != null ? formData.getSbrsCl() : existingSite.getSbrsCl());
        existingSite.setPosblFcltyCl(formData.getPosblFcltyCl() != null ? formData.getPosblFcltyCl() : existingSite.getPosblFcltyCl());
        existingSite.setAnimalCmgCl(formData.getAnimalCmgCl() != null ? formData.getAnimalCmgCl() : existingSite.getAnimalCmgCl());
        existingSite.setFirstImageUrl(formData.getFirstImageUrl() != null ? formData.getFirstImageUrl() : existingSite.getFirstImageUrl());
        existingSite.setMapX(formData.getMapX() != null ? formData.getMapX() : existingSite.getMapX());
        existingSite.setMapY(formData.getMapY() != null ? formData.getMapY() : existingSite.getMapY());
        existingSite.setGnrlSiteCo(formData.getGnrlSiteCo() != null ? formData.getGnrlSiteCo() : existingSite.getGnrlSiteCo());
        existingSite.setAutoSiteCo(formData.getAutoSiteCo() != null ? formData.getAutoSiteCo() : existingSite.getAutoSiteCo());
        existingSite.setGlampSiteCo(formData.getGlampSiteCo() != null ? formData.getGlampSiteCo() : existingSite.getGlampSiteCo());
        existingSite.setCaravSiteCo(formData.getCaravSiteCo() != null ? formData.getCaravSiteCo() : existingSite.getCaravSiteCo());
        existingSite.setBrazierCl(formData.getBrazierCl() != null ? formData.getBrazierCl() : existingSite.getBrazierCl());
        existingSite.setSbrsEtc(formData.getSbrsEtc() != null ? formData.getSbrsEtc() : existingSite.getSbrsEtc());
        existingSite.setGlampInnerFclty(formData.getGlampInnerFclty() != null ? formData.getGlampInnerFclty() : existingSite.getGlampInnerFclty());
        existingSite.setCaravInnerFclty(formData.getCaravInnerFclty() != null ? formData.getCaravInnerFclty() : existingSite.getCaravInnerFclty());
        existingSite.setPrice(formData.getPrice() != null ? formData.getPrice() : existingSite.getPrice());

        // 업데이트 실행
        int result = campingService.updateCampingSite(existingSite);

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("캠핑장 정보 업데이트에 실패했습니다.");
        }

        return ResponseEntity.ok("캠핑장 정보가 성공적으로 업데이트되었습니다.");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 중 오류 발생: " + e.getMessage());
    }
}


}
