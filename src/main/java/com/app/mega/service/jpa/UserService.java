package com.app.mega.service.jpa;

import com.app.mega.dto.request.user.UserRequest;
import com.app.mega.dto.response.user.UserResponse;
import com.app.mega.entity.User;
import com.app.mega.mybatis.UserMapper;
import com.app.mega.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //User정보 불러오기
    @Transactional
    public UserResponse readUserResponse(User user) throws Exception {
        UserResponse userResponse = UserResponse.builder()
                .courseId(user.getId())
                .institutionId(user.getInstitution().getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
        return userResponse;

    }

    //User정보 수정
    @Transactional
    public void updateUserResponse(User user, UserRequest request) throws Exception {

        //데이터 수정
        userMapper.updateUser(request.getName(), request.getPassword(), request.getPhone(), user.getId());
    }

}
