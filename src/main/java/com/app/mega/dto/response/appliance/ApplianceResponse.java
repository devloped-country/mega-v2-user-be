package com.app.mega.dto.response.appliance;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApplianceResponse {
    private Integer status;
    private Long attendance;
    private LocalDateTime time;//dto
    private Long userId;
    private String reason;
}
