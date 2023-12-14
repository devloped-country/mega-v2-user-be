package com.app.mega.controller;

import com.app.mega.dto.request.user.UserRequest;
import com.app.mega.dto.response.user.UserResponse;
import com.app.mega.entity.User;
import com.app.mega.service.jpa.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserContorller {
    private final UserService userService;

    //User 정보 Read
    @GetMapping("/read")
    public UserResponse readUserInfomation(@AuthenticationPrincipal User user) throws Exception {
        System.out.println("readUserInfomation");
        return userService.readUserResponse(user);
    }


    //User 정보 Update
    @PutMapping("/update")
    public void updateUserInformation(@AuthenticationPrincipal User user, UserRequest request) throws Exception {
        userService.updateUserInfo(user,request);
    }

    //User Password Update
    @PutMapping("/updatePassword")
    public void updateUserPassword(@AuthenticationPrincipal User user, UserRequest request) throws Exception {
        userService.isCorrectPassword(user, request);
        userService.updateUserPassword(user, request);
    }

}
