package com.ict.camping.admin.reservations.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ict.camping.admin.reservations.vo.AReservationVO;
@Mapper
public interface AReservationMapper {
    List<AReservationVO> findAllUsage();
    AReservationVO findUsageDetail(String history_idx);

}
