package com.app.mega.service.jpa;

import com.app.mega.dto.response.AttendanceResponse;
import com.app.mega.dto.response.AttendanceSum;
import com.app.mega.dto.response.appliance.ApplianceResponse;
import com.app.mega.dto.request.appliance.ApplianceRequest;
import com.app.mega.dto.response.user.UserResponse;
import com.app.mega.mapper.AttendanceMapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceMapper attendanceMapper;
//    public List<UserResponse> getUserListById(Long id) {
//        return attendanceMapper.getUserListById(id);
//    }

    public AttendanceService(AttendanceMapper attendanceMapper) {
        this.attendanceMapper = attendanceMapper;
    }
    public List<AttendanceResponse> getAttendanceListByUserIdAndMonth(Long userId, int month) {
        List<AttendanceResponse> responses = attendanceMapper.getAttendanceListByUserIdAndMonth(userId, month);
        for (AttendanceResponse response : responses) {
            switch (response.getStatus()) {
                case 0:
                    response.setStatusDescription("미출석");
                    break;
                case 1:
                    response.setStatusDescription("출석");
                    break;
                case 2:
                    response.setStatusDescription("지각");
                    break;
                case 3:
                    response.setStatusDescription("조퇴");
                    break;
                case 4:
                    response.setStatusDescription("공가");
                    break;
                default:
                    response.setStatusDescription("Unknown");
            }
        }
        return responses;
    }
    public AttendanceSum calculateAttendanceSum(Long userId) {
        List<AttendanceResponse> attendanceList = attendanceMapper.getAttendanceListByUserId(userId);

        int attendanceCount = 0;
        int tardinessCount = 0;
        int absenceCount = 0;
        int vacationCount = 0;
        System.out.println("4444");

        for (AttendanceResponse attendance : attendanceList) {
            switch (attendance.getStatus()) {
                case 1:
                    attendanceCount++;
                    break;
                case 2:
                    tardinessCount++;
                    break;
                case 3:
                    absenceCount++;
                    break;
                case 4:
                    vacationCount++;
                    break;
//                //추가할것들
//                default:
            }
        }

        AttendanceSum attendanceSum = new AttendanceSum();
        attendanceSum.setAttendanceCount(String.valueOf(attendanceCount));
        attendanceSum.setTardinessCount(String.valueOf(tardinessCount));
        attendanceSum.setAbsenceCount(String.valueOf(absenceCount));
        attendanceSum.setVacationCount(String.valueOf(vacationCount));

        return attendanceSum;
    }

//    public void saveChangeAttendanceStatus(Long userId,Integer changeStatus) {
//        attendanceMapper.saveChangeAttendanceStatus(userId, changeStatus);
//    }
//    public AttendanceResponse getAttendanceResponse(@Param("attendanceId") Long attendanceId){
//        return attendanceMapper.getAttendanceResponse(attendanceId);
//    }



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