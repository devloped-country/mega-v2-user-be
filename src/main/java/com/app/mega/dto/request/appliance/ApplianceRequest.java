package com.app.mega.dto.request.appliance;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ApplianceRequest {
    private String reason;//dto
    private LocalDate applianceDate;
//    private Attendance attendanceId;
//    private User userId;
//    public ApplianceRequest(){}
    private Integer status;//공가공결 신청하는 사람의 원하는 상태값

}
