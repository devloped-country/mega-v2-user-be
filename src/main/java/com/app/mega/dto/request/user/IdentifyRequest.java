package com.app.mega.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IdentifyRequest {
    //본인인증 요청 DTO
    private String name;
    private String email;
    private String phone;
    private String identifyMethod;
}
