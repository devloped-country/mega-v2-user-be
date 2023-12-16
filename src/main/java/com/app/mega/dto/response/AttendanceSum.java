package com.app.mega.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceSum {
    private String attendanceCount;
    private String tardinessCount;
    private String absenceCount;
    private String vacationCount;
}
