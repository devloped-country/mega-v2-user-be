package com.app.mega.controller;

import com.app.mega.common.handler.CustomException;
import com.app.mega.dto.request.user.*;
import com.app.mega.dto.response.user.AuthenticationResponse;
import com.app.mega.service.jpa.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @GetMapping("/checklogin")
    public int checklogin() {
        return 1;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
        /* 본인인증 안된 유저라면 반환값
        {
            "token": null,
            "isIdentified": false
        }
         */
    }

    //본인인증: 최초로그인 시 이름,이메일,전화번호 입력받아서 등록된 교육생인지 확인
    //identifyMethod: phone/email
    @PostMapping("/identify/check")
    public ResponseEntity identify(@RequestBody IdentifyRequest request) throws CustomException {
        System.out.println("AuthenticationController.identifyPhone");
        System.out.println("request = " + request);
        return authenticationService.checkForIdentify(request.getEmail(), request.getPhone(), request.getName(), request.getIdentifyMethod());
    }

    //인증번호 확인
    @PostMapping("/identify/certificate")
    public ResponseEntity certificate(@RequestBody CertificationRequest request) throws CustomException {
        return authenticationService.certificate(request);
    }

    //비밀번호 재설정
    @PutMapping("/reset_password")
    public ResponseEntity certificate(@RequestBody ResetPasswordRequest request) throws CustomException {
        return authenticationService.resetPassword(request.getId(), request.getPassword());
    }
}