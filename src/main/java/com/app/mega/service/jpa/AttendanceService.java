package com.app.mega.service.jpa;

import com.app.mega.dto.response.appliance.ApplianceResponse;
import com.app.mega.dto.request.appliance.ApplianceRequest;
import com.app.mega.mapper.AttendanceMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;

    public AttendanceService(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }


    public List<ApplianceResponse> getAppliancesById(Long id) {
        return attendanceMapper.getAppliancesById(id);
    }

    public Long getAttendanceId(Long id,LocalDate date) {
        return attendanceMapper.getAttendanceId(id,date);
    }

    public void createApplianceForAttendance(Long id, ApplianceRequest applianceRequest, Long attendanceId) {
        attendanceMapper.createApplianceForAttendance(id, applianceRequest, attendanceId);
    }
}