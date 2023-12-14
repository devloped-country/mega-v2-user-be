package com.app.mega.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {
    private Long id;

    private String name;
    private String password;
    private String phone;
}
