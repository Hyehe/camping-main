package com.ict.camping.admin.reservations.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AReservationVO {
    private int history_idx;
    private int user_idx;
    private int contentId;
    private String action_type;
    private String action_date;
    private String created_at;
    private int payment_amount;

    // join
    private String username;
    private String facltNm;
}
