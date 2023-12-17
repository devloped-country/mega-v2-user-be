package com.app.mega.dto.response;


import com.app.mega.dto.response.user.UserResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AttendanceListAndTotal {
    //    private Long id;//교육생id
//    private UserResponse userResponse;
    private List<AttendanceResponse> AttendanceResponse;
    private AttendanceSum attendanceSum;
//    private Map<Integer, Long> statusCountMap;//추가
}