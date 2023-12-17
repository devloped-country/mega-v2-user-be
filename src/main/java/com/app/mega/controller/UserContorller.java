package com.app.mega.controller;

import com.app.mega.common.CommonResponse;
import com.app.mega.dto.request.user.PasswordRequest;
import com.app.mega.dto.request.user.UserRequest;
import com.app.mega.dto.response.user.UserResponse;
import com.app.mega.entity.User;
import com.app.mega.service.jpa.AuthenticationService;
import com.app.mega.service.jpa.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserContorller {
    private final UserService userService;
    private final AuthenticationService authenticationService;

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

}
