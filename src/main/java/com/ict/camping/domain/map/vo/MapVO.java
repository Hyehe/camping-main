package com.ict.camping.domain.map.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapVO {
    private int contentId;
    private String facltNm;
    private String intro;
    private String lineIntro;
    private Integer allar;
    private String bizrno;
    private String featureNm;
    private String induty;
    private String lctCl;
    private String doNm;
    private String sigunguNm;
    private String zipcode;
    private String addr1;
    private Double mapX;
    private Double mapY;
    private String direction;
    private String tel;
    private String homepage;
    private Integer sitedStnc;
    private Integer siteMg1Width;
    private Integer siteMg2Width;
    private Integer siteMg3Width;
    private Integer siteMg1Virticl;
    private Integer siteMg2Virticl;
    private Integer siteMg3Virticl;
    private Integer siteBottomCl2;
    private Integer siteBottomCl3;
    private Integer siteBottomCl4;
    private Integer siteBottomCl5;
    private String sbrsCl;
    private String posblFcltyCl;
    private String animalCmgCl;
    private String firstImageUrl;
    private String createdtime;
    private Integer siteBottomCl1;
    private String gnrlSiteCo;
    private String autoSiteCo;
    private String glampSiteCo;
    private String caravSiteCo;
    private String brazierCl;
    private String sbrsEtc;
    private String glampInnerFclty;
    private String caravInnerFclty;
    private String price;
    private MultipartFile file;

}