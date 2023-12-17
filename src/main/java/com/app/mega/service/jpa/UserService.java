package com.app.mega.service.jpa;

import com.app.mega.common.CommonResponse;
import com.app.mega.dto.request.user.PasswordRequest;
import com.app.mega.dto.request.user.UserRequest;
import com.app.mega.dto.response.user.UserResponse;
import com.app.mega.entity.User;
import com.app.mega.mybatis.UserMapper;
import com.app.mega.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //User정보 수정  //이름,휴대폰번호 변경
    @Transactional
    public void updateUserInfo(User user, UserRequest request) {
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        userRepository.save(user);
    }

    //User password 수정   //비밀번호 변경
    @Transactional
    public ResponseEntity updateUserPassword(User user, String rawPassword) {
        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .responseCode(1)
                        .responseMessage("[성공] 비밀번호 변경 완료")
                        .data(null)
                        .build());
    }

    //기존 password 확인
    @Transactional
    public boolean isCorrectPassword(User user, PasswordRequest request){
        return passwordEncoder.matches(request.getExistedPassword(), user.getPassword());
    }

}
