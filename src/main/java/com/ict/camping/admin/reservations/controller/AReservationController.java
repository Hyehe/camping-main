package com.ict.camping.admin.reservations.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.camping.admin.reservations.service.AReservationService;
import com.ict.camping.admin.reservations.vo.AReservationVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/reservation")
public class AReservationController {
    @Autowired
    private AReservationService reservationService;

    @GetMapping("/usage")
    public List<AReservationVO> getAllUsage() {
        List<AReservationVO> allAdmins = reservationService.getAllUsage();
        return allAdmins;
    }

    @GetMapping("/usage/{history_idx}")
    public AReservationVO getUsageDetail(@PathVariable("history_idx") String history_idx) {
        return reservationService.getUsageDetail(history_idx);
    }
    
    


    
}
