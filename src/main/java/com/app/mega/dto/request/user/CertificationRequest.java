package com.app.mega.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRequest {
    //인증번호 확인요청 DTO
    private Long id;
    private int certificationNumber;
}