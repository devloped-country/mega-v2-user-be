package com.app.mega.service.jpa;

import com.app.mega.dto.request.user.PasswordRequest;
import com.app.mega.dto.request.user.UserRequest;
import com.app.mega.dto.response.user.UserResponse;
import com.app.mega.entity.User;
import com.app.mega.mybatis.UserMapper;
import com.app.mega.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //User정보 불러오기
    @Transactional
    public UserResponse readUserResponse(User user) throws Exception {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .courseName(user.getCourse().getName())
                .institutionName(user.getInstitution().getName())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
        return userResponse;

    }

    //User정보 수정
    @Transactional
    public void updateUserInfo(User user, UserRequest request) throws Exception {

        System.out.println("user = " + user);
        System.out.println("request = " + request);
        //데이터 수정
        userMapper.updateUser(request.getName(), request.getPhone(), user.getId());

    }

    //User password 수정
    @Transactional
    public void updateUserPassword(User user, PasswordRequest request) throws Exception {
        userMapper.updatePassword(passwordEncoder.encode(request.getEditPassword()), user.getId());
    }

    //기존 password 확인
    @Transactional
    public boolean isCorrectPassword(User user, PasswordRequest request) throws Exception {
        return Objects.equals(passwordEncoder.encode(request.getExistedPassword()), user.getPassword());
    }

}
