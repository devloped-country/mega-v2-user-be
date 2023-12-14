package com.app.mega.dto.response.appliance;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ApplianceResponse {
    private Integer status;
    private Long attendance;
    private LocalDate time;//dto
    private Long userId;
    private String reason;
}
//    private String reason;
//    private LocalDate applianceDate= LocalDate.now();//dto
//    //    private Attendance attendanceId;
////    private User userId;
//    private Integer status;