package com.ict.camping.admin.reservations.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.camping.admin.reservations.mapper.AReservationMapper;
import com.ict.camping.admin.reservations.vo.AReservationVO;


@Service
public class AReservationServiceImpl implements AReservationService{
    @Autowired
    private AReservationMapper reservationMapper;

    @Override
    public List<AReservationVO> getAllUsage() {
        return reservationMapper.findAllUsage();
    }

    @Override
    public AReservationVO getUsageDetail(String history_idx) {
        return reservationMapper.findUsageDetail(history_idx);
    }
   

    
}
