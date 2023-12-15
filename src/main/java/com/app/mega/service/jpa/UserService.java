package com.app.mega.service.jpa;

import com.app.mega.dto.request.user.UserRequest;
import com.app.mega.dto.response.user.UserResponse;
import com.app.mega.entity.User;
import com.app.mega.mybatis.UserMapper;
import com.app.mega.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        //데이터 수정
        userMapper.updateUser(request.getName(), request.getPassword(), user.getId());
        System.out.println("user = " + user);
        System.out.println("request = " + request);
    }

    //User password 수정
    public void updateUserPassword(User user, UserRequest request) throws Exception {
        userMapper.updatePassword(request.getPassword(), user.getId());
    }

    //기존 password 확인
    public boolean isCorrectPassword(User user, UserRequest request) throws Exception {
        return passwordEncoder.encode(request.getPassword()) == passwordEncoder.encode(user.getPassword());
    }

}
