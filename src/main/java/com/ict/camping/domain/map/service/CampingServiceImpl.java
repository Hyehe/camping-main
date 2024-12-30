package com.ict.camping.domain.map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.camping.domain.map.mapper.CampingMapper;
import com.ict.camping.domain.map.vo.MapVO;

@Service
public class CampingServiceImpl implements CampingService{

    @Autowired
    private CampingMapper campingMapper;

    // 모든 캠핑장 데이터를 가져오는 메서드
    @Override
    public List<MapVO> getAllCampingSites() {
        return campingMapper.findAllCampingSites();
    }

    @Override
    public MapVO findCampingSiteByContentId(String contentId) {
        return campingMapper.findCampingSiteByContentId(contentId);
    }

    @Override
    public void saveCampingSite(MapVO mapVO) {
        campingMapper.insertCampingSite(mapVO);
    }

    @Override
    public int insertCampingSite(MapVO mapVO) {
        return campingMapper.insertCampingSite(mapVO);
    }

    @Override
    public int updateCampingSite(MapVO mapVO) {
        return campingMapper.updateCampingSite(mapVO); // Mapper에서 업데이트 수행
    }

    @Override
    public boolean isContentIdDuplicate(String contentId) {
        return campingMapper.checkCampingDuplicate(contentId) > 0;
    }
}