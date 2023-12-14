package com.app.mega.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String courseName;
    private String institutionName;

    private Long id;
    private String name;
    private String email;
    private String phone;


}
