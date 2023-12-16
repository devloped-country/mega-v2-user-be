package com.app.mega.controller;


import com.app.mega.dto.request.appliance.ApplianceRequest;
import com.app.mega.dto.response.AttendanceListAndTotal;
import com.app.mega.dto.response.AttendanceProfileSum;
import com.app.mega.dto.response.AttendanceResponse;
import com.app.mega.dto.response.AttendanceSum;
import com.app.mega.dto.response.appliance.ApplianceResponse;
import com.app.mega.dto.response.user.UserResponse;
import com.app.mega.entity.Attendance;
//import com.app.mega.repository.AttendanceRepository;
import com.app.mega.entity.User;
import com.app.mega.service.jpa.AttendanceService;
//import com.app.mega.service.mybatis.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@Getter
@Setter
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor

public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("/getAppliancesById")
    public List<ApplianceResponse> getAppliancesById(@AuthenticationPrincipal User user) {

        //public List<ApplianceResponse> getAppliancesById(@PathVariable("id") Long id) {
        //return AttendanceRepository.findById(id).orElse(null);
        //return (List<ApplianceResponse>) attendanceService.getAppliancesById(id);

        return attendanceService.getAppliancesById(user.getId());
    }
    @PostMapping("/appliance")
    public void createApplianceForAttendance(@AuthenticationPrincipal User user, @RequestBody ApplianceRequest applianceRequest) {
        //유저id, applianceRequest->(reason,applianceDate,status)//유저8번으로 테스트
        //!!!!!!!!!!!근데 이거 요청을"applianceDate": "2023-01-01",형식으로 보내져야한다!!!!!!!!!!!
        // 출석정보 테이블의 id를 찾기 위해 ( 교육생id/공가신청의 날짜) 를 통해서 해당출결id를 찾음
        Long attendanceId = attendanceService.getAttendanceId(user.getId(), applianceRequest.getApplianceDate());


        // 공가신청내용의DTO, url의 교육생 id, 찾은 출석 테이블 id를 Appliance에 저장 한다
        attendanceService.createApplianceForAttendance(user.getId(), applianceRequest, attendanceId);

    }

    @GetMapping("/AttendancetTotal")
    public AttendanceListAndTotal TotalById(@AuthenticationPrincipal User user) {
//        List<UserResponse> userList = attendanceService.getUserListById(user.getId());


            AttendanceListAndTotal attendanceListAndTotal = new AttendanceListAndTotal();
//            profileSum.setUserResponse(userResponse);

            // Get the current month
            int currentMonth = LocalDate.now().getMonthValue();
                //여기를 현제말고 프론트에 받아온걸로 달을 주자!!!!!!!!!

            List<AttendanceResponse> attendanceList = attendanceService.getAttendanceListByUserIdAndMonth(user.getId(), currentMonth);
            attendanceListAndTotal.setAttendanceResponse(attendanceList);

            AttendanceSum attendanceSum = attendanceService.calculateAttendanceSum(user.getId());
            attendanceListAndTotal.setAttendanceSum(attendanceSum);

            Map<Integer, Long> statusCountMap = attendanceList.stream()
                    .collect(Collectors.groupingBy(AttendanceResponse::getStatus, Collectors.counting()));

        return attendanceListAndTotal; ///이것만 좀 해결하고
    }

//    @GetMapping("/{id}/AttendancetTotal")
//    public List<AttendanceProfileSum> TotalById(@AuthenticationPrincipal User user) {
//        List<UserResponse> userList = attendanceService.getUserListById(user.getId());
//
//        List<AttendanceProfileSum> profileSumList = new ArrayList<>();
//        for (UserResponse userResponse : userList) {
//            AttendanceProfileSum profileSum = new AttendanceProfileSum();
//            profileSum.setUserResponse(userResponse);
//
//            // Get the current month
//            int currentMonth = LocalDate.now().getMonthValue();
//
//            List<AttendanceResponse> attendanceList = attendanceService.getAttendanceListByUserIdAndMonth(userResponse.getId(), currentMonth);
//            profileSum.setAttendanceResponse(attendanceList);
//
//            AttendanceSum attendanceSum = attendanceService.calculateAttendanceSum(userResponse.getId());
//            profileSum.setAttendanceSum(attendanceSum);
//
//            Map<Integer, Long> statusCountMap = attendanceList.stream()
//                    .collect(Collectors.groupingBy(AttendanceResponse::getStatus, Collectors.counting()));
//
//            profileSum.setStatusCountMap(statusCountMap);
//
//            profileSumList.add(profileSum);
//        }
//
//        return profileSumList;
//    }
}
