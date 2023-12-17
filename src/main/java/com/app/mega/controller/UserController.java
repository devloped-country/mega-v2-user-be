package com.app.mega.controller;

import com.app.mega.common.CommonResponse;
import com.app.mega.dto.request.LocationRequest;
import com.app.mega.dto.request.UserSaveRequest;
import com.app.mega.dto.request.user.PasswordRequest;
import com.app.mega.dto.request.user.UserRequest;
import com.app.mega.dto.response.user.UserResponse;
import com.app.mega.entity.Admin;
import com.app.mega.entity.User;
import com.app.mega.service.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserManageService userManageService;
    private final UserServiceJpa userServiceJpa;
    private final InstitutionServiceJpa institutionServiceJpa;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    // 유저등록 컨트롤러
    @PostMapping("/save_user")
    public ResponseEntity register(@RequestBody UserSaveRequest request, @AuthenticationPrincipal Admin admin) throws Exception {
        return userManageService.userSave(request, admin.getInstitution());
    }
    /*
    {
                    "name": "송정희",
                    "email": "sjh8924@naver.com",
                    "phone": "01033802064"
                    "course": ??
}
     */


    //User 정보 Read
    @GetMapping("/read")
    public UserResponse readUserInfomation(@AuthenticationPrincipal User user) throws Exception {
        System.out.println("readUserInfomation");
        return userService.readUserResponse(user);
    }

    //User 정보 Update
    @PutMapping("/update")
    public void updateUserInformation(@AuthenticationPrincipal User user, @RequestBody UserRequest request) throws Exception {
        userService.updateUserInfo(user,request);
    }

    //User Password Update
    @PutMapping("/updatePassword")
    public ResponseEntity updateUserPassword(@AuthenticationPrincipal User user, @RequestBody PasswordRequest request) throws Exception {
        if(userService.isCorrectPassword(user, request)) {
            return userService.updateUserPassword(user, request.getEditPassword());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    CommonResponse.builder()
                            .responseCode(-1)
                            .responseMessage("[실패] 기존 비밀번호 불일치")
                            .data(null)
                            .build());
        }
    }


    @PostMapping
    public ResponseEntity<CommonResponse<Boolean>> getLocation(@RequestBody LocationRequest locationRequest) {
        Boolean isCorrectLocation = userServiceJpa.findInstitutionIdByEmail(locationRequest);

        if(isCorrectLocation) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    CommonResponse.<Boolean>builder().responseCode(1).responseMessage("성공")
                            .data(isCorrectLocation).build());
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<Boolean>builder().responseCode(-1).responseMessage("에러")
                        .data(isCorrectLocation).build());
    }
}
