package com.ict.camping.domain.map.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ict.camping.domain.map.vo.MapVO;
@Mapper
public interface CampingMapper {
    // 모든 캠핑장 데이터를 조회하는 메서드
  
    List<MapVO> findAllCampingSites();

     // contentId를 사용하여 특정 캠핑장 정보를 조회하는 메서드
   
     MapVO findCampingSiteByContentId(String contentId);

 
    int checkCampingDuplicate(String contentId);

    // 캠핑장 데이터를 삽입
   
    int insertCampingSite(MapVO mapVO);

  
int updateCampingSite(MapVO mapVO);


}