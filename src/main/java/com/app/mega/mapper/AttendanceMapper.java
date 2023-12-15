package com.app.mega.mapper;



import com.app.mega.dto.request.appliance.ApplianceRequest;
import com.app.mega.dto.response.appliance.ApplianceResponse;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AttendanceMapper {


    List<ApplianceResponse> getAppliancesById(Long id);


    void createApplianceForAttendance(Long id, ApplianceRequest applianceRequest, Long attendanceId);


    Long getAttendanceId(Long id, LocalDate date);
}
