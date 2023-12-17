package com.app.mega.mapper;



import com.app.mega.dto.request.appliance.ApplianceRequest;
import com.app.mega.dto.response.AttendanceResponse;
import com.app.mega.dto.response.appliance.ApplianceResponse;
import com.app.mega.dto.response.user.UserResponse;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AttendanceMapper {


    List<ApplianceResponse> getAppliancesById(Long id);


    void createApplianceForAttendance(Long id, ApplianceRequest applianceRequest, Long attendanceId);


    Long getAttendanceId(Long id, LocalDate date);
    List<AttendanceResponse> getAttendanceListByUserIdAndMonth(Long userId, int month);
//    List<UserResponse> getUserListById(Long id);

    List<AttendanceResponse> getAttendanceListByUserId(Long userId);

}
