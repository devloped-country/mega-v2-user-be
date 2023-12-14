package com.app.mega.dto.response.user;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    //로그인 응답 DTO
    private String token;
    private Boolean isIdentified;
    private Long id;
    private String email;
    private Long courseId;
}