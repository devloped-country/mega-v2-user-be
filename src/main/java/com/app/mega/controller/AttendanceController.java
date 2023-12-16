package com.app.mega.controller;


//import com.app.mega.dto.response.*;
import com.app.mega.dto.request.appliance.ApplianceRequest;
import com.app.mega.dto.response.appliance.ApplianceResponse;
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


import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:3000")


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

//        public List<ApplianceResponse> getAppliancesById(@PathVariable("id") Long id) {
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
}
