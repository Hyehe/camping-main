package com.ict.camping.admin.reservations.service;

import java.util.List;

import com.ict.camping.admin.reservations.vo.AReservationVO;

public interface AReservationService {
    // 리스트
    List<AReservationVO> getAllUsage();
    // 디테일
    AReservationVO getUsageDetail(String history_idx);
} 
